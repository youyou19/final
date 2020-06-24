package mum.edu.controller;

import mum.edu.model.*;
import mum.edu.service.*;
import mum.edu.serviceImpl.CategoryServiceImpl;
import mum.edu.serviceImpl.ProductServiceImpl;
import mum.edu.serviceImpl.RoleServiceImpl;
import mum.edu.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@ControllerAdvice
public class PrincipalController {
    //public static String rootDirectory=System.getProperty("user.dir")+"\\uploads1";
  //  public static String rootDirectory=System.getProperty("user.dir");
   // public static String folder="\\uploads1";
    public static String rootDirectory=System.getProperty("user.dir")+"/src/main/resources/static/images/uploads/";
    private User currentUser=null;

    @Autowired
    private ServletContext context;
    @Autowired
    private HttpSession session;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AdsService adsService;

    @Autowired
    private FollowService followService;


    @Autowired
    private OrderItemService orderItemService;


    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    HashMap<Long,OrderItem> items = new HashMap<>();

    /******************************************************securite************************************/

@ModelAttribute("currentUser")
public User getCurrentUser(Authentication authentication, Model model) {
    if (authentication == null) return null;
    else {
        Object dr = authentication.getName();
        currentUser = userService.findUserByEmail(dr.toString());
        model.addAttribute("currentUser", currentUser);
        return currentUser;
    }
}
    //******************Shop Controller for Every one  ***************************************************************
    @GetMapping("/public/shop")
    public String shop(Model model){
        model.addAttribute("products",productService.findAll());
        return "shop";
    }
    @GetMapping("/public/detailPage/{id}")
    public String detailProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("product",productService.findProduct(id));
        return "productDetails";
    }


    //*****End*************************************************************************************
//******************shoping cart Controller for Every one  *************************************
    //******************shoping cart Controller for Every one  *************************************

    @GetMapping("/public/cart/add/{idProd}")
    public String addToCart(@PathVariable("idProd") Long idProd,Model model){
        //model.addAttribute("nbrOfItem",items.size());
        items.put(idProd,new OrderItem(productService.findProduct(idProd),1));
        System.out.println("Add cart*********Item Size"+items.size());

        return "redirect:/public/shop";
    }

    @GetMapping("/public/cart/delete/{idProd}")
    public String deleteFromCart(@PathVariable("idProd") Long idProd){
        items.remove(idProd);
        System.out.println("Delete cart********************************************************");
        return "redirect:/public/cart";
    }


    @GetMapping("/public/cart")
    public String detailCart(Model model){
        model.addAttribute("nbrOfItem",items.size());
        model.addAttribute("carteItems",items.values());
        Double sum=0.0;
        for(OrderItem orderItem : items.values()){
            sum+=orderItem.getPrice();
        }
        model.addAttribute("PriceTot"+sum);
        return "carteDetails";
    }

    //*****Order************************************************************************

    @GetMapping("/authorized/order")
    public String makeOrder(@ModelAttribute Payment payment,Model model){
        model.addAttribute("nbrOfItem",items.size());
        model.addAttribute("carteItems",items.values());
        System.out.println("li nan autorized user a pou order a :");
        return "paymentForm.html";
    }


    //******End************************************************************************************
    //********************************Log in******************************************

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
        @RequestMapping(value = "/error/access-denied", method = RequestMethod.GET)
        public String accessDenied() {
            return "403";
        }

        @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    @ModelAttribute("roles")
    public List<Role> getRoles(Model model) {
        return roleService.findAll();
    }

    @GetMapping(value = "/registration")
    public String registration(@ModelAttribute("user") User user) {
       return "registration";
    }

    @PostMapping(value = "/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
//        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            user.getRoles().forEach(System.out::println);
            userService.saveUser(user);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
            return "redirect:/login";
          //  return "registration";

        }
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        currentUser=null;
        return "redirect:/login?logout=true";
    }
//**************Users********************

    @GetMapping("/admin/registrations")
    public String registrationUserForm(@ModelAttribute("user") User user, Model model){
        model.addAttribute("roles",roleService.findRole());
        return "UserRegistrationForm";
    }
    //
    @PostMapping(value="/admin/registrations")
    public String addUser(@Valid @ModelAttribute("user")  User user, BindingResult result,Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("user",user);
            return "UserRegistrationForm";
        }else {

            model.addAttribute("successmessage","Payment a ete enregistre avec succes!");
            userService.saveUser(user);
            return "redirect:/admin/userList";
        }
    }

    @GetMapping(value="/getRegistrations/{id}")
    public String getRegistrations(@PathVariable Long id, Model model) {
        User userFind=userService.findById(id);
        model.addAttribute("user",userFind);
        return "editRegistrationUser";
    }

    @GetMapping(value="/approvalRegistrations/{id}")
    public String approvalRegistrations(@PathVariable Long id, Model model) {
        User userFind=userService.findById(id);
        model.addAttribute("user",userFind);
        return "approvalRegistration";
    }

    @PostMapping(value="/admin/editRegistrations")
    public String editUser(@Valid @ModelAttribute("user")  User user, BindingResult result,Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("user",user);
            return "UserRegistrationForm";
        }else {
            model.addAttribute("successmessage","Payment a ete enregistre avec succes!");
            userService.saveUser(user);
            return "redirect:/admin/userList";
        }
    }


    @PostMapping(value="/admin/approvalRegistrations")
    public String approvalUser(@Valid @ModelAttribute("user")  User user, BindingResult result,Model model) {
//        if(result.hasErrors()) {
//            model.addAttribute("errormessage","Enregistrement echoue!");
//            model.addAttribute("user",user);
//            return "UserRegistrationForm";
//        }else {
             User userApproval=userService.findById(user.getId());
             userService.approvalSeller(userApproval);
            model.addAttribute("successmessage","Approved!");

            //userService.saveUser(user);
            return "redirect:/admin/userList";
        //}
    }

    @GetMapping("/admin/userList")
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        return "userList";
    }


/*
    @GetMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/admin/index")
    public String adminIndex(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        model.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
        return "user/admin";
    }

    @GetMapping("/dba/index")
    public String dbaIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        model.addAttribute("adminMessage","Content Available Only for Users with DBA Role");
        return "user/dba";
    }
*/
    @GetMapping("/admin")
    public String principal(){
        return "index";
    }

/********************************** Category *************************************************/
  @GetMapping("/admin/categoryList")
  public String categoryList(Model model){
    model.addAttribute("categoryList",categoryService.findAll());
    return "categoryList";
}
    @GetMapping("/admin/category")
    public String categoryForm(@ModelAttribute("category") Category category, Model model){
        return "categoryForm";
    }

    @GetMapping(value="/getCategory/{id}")
    public String getCategory(@PathVariable Long id, Model model) {
        Category categoryFind=categoryService.findCategory(id);
        model.addAttribute("category",categoryFind);
        return "editCategory";
    }

    @PostMapping(value="/admin/category")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        String role="";
      if(result.hasErrors()) {
            model.addAttribute("category",category);
            return "categoryForm";
        }else {
          if(currentUser!=null && currentUser!=null)
          role = userService.findUserByRole(currentUser.getId());

          if (!role.equalsIgnoreCase("ADMIN") || currentUser.getActive()==0) {
              model.addAttribute("errormessage", currentUser.getUserName()+" Please contact the Administrator!");
              return "categoryForm";
          }
          else {
              category.setAdmin(currentUser);
             // redirectAttributes.addFlashAttribute("successmessage", "Category a ete enregistre avec succes!");
              categoryService.saveCategory(category);
              return "redirect:/admin/categoryList";
          }
      }
    }

    @PostMapping(value="/admin/editCategory")
    public String editCategory(@ModelAttribute("category") Category category, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("category",category);
            return "editCategory";
        }else {
            redirectAttributes.addFlashAttribute("successmessage","Category a ete enregistre avec succes!");
            Category categoryUpdate = categoryService.updateCategory(category);
        return "redirect:/admin/categoryList";
        }
    }


    @GetMapping(value="/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("successmessage","Category a ete supprime avec succes!");
        return "redirect:/admin/categoryList";
    }



    @GetMapping("/admin/product")
    public String productForm(@ModelAttribute("product") Product product, Model model){
      model.addAttribute("categories",categoryService.findAll());
        return "addProduct";
    }

    @PostMapping(value="/admin/product")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, RedirectAttributes redirectAttributes,  HttpServletRequest request, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("product",product);
            model.addAttribute("categories",categoryService.findAll());
            return "addProduct";
        }else {
            String role="";
            if(currentUser!=null && currentUser!=null)
                role = userService.findUserByRole(currentUser.getId());

            if (!role.equalsIgnoreCase("SELLER") || currentUser.getApproved()==0) {
                model.addAttribute("errormessage", currentUser.getUserName()+", you must be approval by the Admin!");
                return "addProduct";
            }
            else {
                product.setSeller(currentUser);

                String idImage=null;
                MultipartFile productImage = product.getImageFile();
                if (productImage != null && !productImage.isEmpty()) {
                    try {
                        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                        idImage= product.getName()+"_"+ sdfDate.format(new Date())+"_"+new Date().getHours()
                                +"_"+new Date().getMinutes()+"_"+new Date().getSeconds();
                        String imgPath = rootDirectory+idImage+".png";
                        byte[] bytes = productImage.getBytes();
                        Path path = Paths.get(imgPath);
                        Files.write(path,bytes);

                    } catch (Exception e) {
                        throw new RuntimeException("Product Image saving failed", e);
                    }
                }
              // redirectAttributes.addFlashAttribute("successmessage","Category a ete enregistre avec succes!");
                product.setImageId(idImage+".png");
                productService.saveProduct(product);










/*


                String idImage=null;
            MultipartFile productImage = product.getImageFile();
            if (productImage != null && !productImage.isEmpty()) {
                try {
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                    idImage= product.getName()+"_"+ sdfDate.format(new Date())+"_"+new Date().getHours()
                            +"_"+new Date().getMinutes()+"_"+new Date().getSeconds()+".png";
                    productImage.transferTo(new File(rootDirectory+"\\" + idImage.trim()));
                } catch (Exception e) {
                    throw new RuntimeException("Product Image saving failed", e);
                }
            }
            redirectAttributes.addFlashAttribute("successmessage","Category a ete enregistre avec succes!");
 */
            return "redirect:/admin/productList";
        }
    }}

    @GetMapping("/admin/productList")
    public String productList(Model model){
        User userSearch=null;
      if(currentUser!=null) {
          userSearch = userService.findById(currentUser.getId());
          model.addAttribute("productList", productService.findProductBySeller(userSearch.getId()));
      }
//        model.addAttribute("productList",productService.findAll());
        return "productList";
    }
    @GetMapping(value="/getProduct/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product productFind=productService.findProduct(id);
        model.addAttribute("category",categoryService.findAll());
        model.addAttribute("product",productFind);
        return "editProduct";
    }


    @PostMapping(value="/admin/editProduct")
    public String editProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("product",product);
            return "editProduct";
        }else {
            redirectAttributes.addFlashAttribute("successmessage","Product a ete enregistre avec succes!");
            Product productUpdate = productService.updateProduct(product);

            return "redirect:/admin/productList";
        }
    }


    @GetMapping(value="/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);

        redirectAttributes.addFlashAttribute("successmessage","Product a ete supprime avec succes!");
        return "redirect:/admin/productList";
    }


    //Import and Export
    @GetMapping(value="/admin/categoryPdf")
    public void categoryPDF(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList=categoryService.findAll();
        boolean isFlag=categoryService.createPDF(categoryList, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"categories"+".pdf");
            filedownload(fullPath,response, "categories.pdf");
        }
    }

    @GetMapping(value="/admin/productPdf")
    public void productPDF(HttpServletRequest request, HttpServletResponse response) {
        List<Product> productList=productService.findAll();
        boolean isFlag=productService.createPDF(productList, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"products"+".pdf");
            filedownload(fullPath,response, "products.pdf");
        }
    }


    @GetMapping(value="/admin/orderPdf/{id}")
    public void orderPDF(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
         Order order=orderService.findOrder(id);
        boolean isFlag=orderService.createPDF(order, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"orders"+".pdf");
            filedownload(fullPath,response, "orders.pdf");
        }

    }
    @GetMapping(value="/admin/orderExcel/{id}")
    public void orderExcel(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        Order order=orderService.findOrder(id);
        boolean isFlag=orderService.createExcel(order, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"orders"+".pdf");
            filedownload(fullPath,response, "orders.pdf");
        }

    }


    @GetMapping(value="/admin/categoryCsv")
    public void categoryCSV(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList=categoryService.findAll();
        boolean isFlag=categoryService.createCSV(categoryList, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"categories"+".csv");
            filedownload(fullPath,response, "categories.csv");
        }
    }
    @GetMapping(value="/admin/productCsv")
    public void productCSV(HttpServletRequest request, HttpServletResponse response) {
        List<Product> productList=productService.findAll();
        boolean isFlag=productService.createCSV(productList, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"products"+".csv");
            filedownload(fullPath,response, "products.csv");
        }
    }

    private void filedownload(String fullPath, HttpServletResponse response, String filename) {
        File file=new File(fullPath);
        final int BUFFER_SIZE=4096;
        if(file.exists()) {
            try {
                FileInputStream inputStream=new FileInputStream(file);
                String mimeType=context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition","attachment; filename="+filename);
                OutputStream outputStream=response.getOutputStream();
                byte buffer[]= new byte[BUFFER_SIZE];
                int byteReads=-1;
                while((byteReads=inputStream.read(buffer))!=-1) {
                    outputStream.write(buffer,0, byteReads);
                }
                inputStream.close();
                outputStream.close();
                file.delete();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @GetMapping(value="/admin/categoryExcel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList=categoryService.findAll();
        boolean isFlag=categoryService.createExcel(categoryList, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"categories"+".xls");
            filedownload(fullPath,response, "categories.xls");
        }
    }


    @GetMapping(value="/admin/productExcel")
    public void productExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Product> productList=productService.findAll();
        boolean isFlag=productService.createExcel(productList, context, request, response);
        if(isFlag) {
            String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"products"+".xls");
            filedownload(fullPath,response, "products.xls");
        }
    }

    //*********************************************************Address*************************************888
    @ModelAttribute(name="addressType")
    public Map<String, String > getAddressType(Model model){
        Map<String, String> map=new HashMap<>();
        map.put("BILLING","BILLING");
        map.put("SHIPPING","SHIPPING");
        model.addAttribute("addressType",map);
        return map;
    }

    @GetMapping(value="/admin/address")
    public String addressForm(@ModelAttribute("address") Address address){
        return "addAddress";
    }

    @PostMapping(value="/admin/address")
    public String addAddress(@Valid @ModelAttribute("address") Address address, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("address",address);
            return "addAddress";
        }else {

            redirectAttributes.addFlashAttribute("successmessage","Address a ete enregistre avec succes!");
            addressService.saveAddress(address);
            return "redirect:/admin/addressList";
        }
    }

    @PostMapping(value="/admin/editAddress")
    public String ediAddress(@Valid @ModelAttribute("address") Address address, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("address",address);
            return "addAddress";
        }else {

            redirectAttributes.addFlashAttribute("successmessage","Address a ete enregistre avec succes!");
            addressService.saveAddress(address);
            return "redirect:/admin/addressList";
        }
    }

    @GetMapping(value="/admin/getAddress/{id}")
    public String getAddress(@PathVariable Long id, Model model) {
        Address addressFind=addressService.findAddress(id);
        model.addAttribute("address",addressFind);
        return "editAddress";
    }

    @GetMapping(value="/admin/addressList")
    public String AddressList(Model model) {
        List<Address> addressList = addressService.getAllAddress();

        List<Address> list = new ArrayList<>();

        for (Address adr : addressList){
            if (adr.getType() == AddressType.BILLING)
                adr.setTypeAddress("BILLING");
            else
                adr.setTypeAddress("SHIPPING");
            list.add(adr);
    }
        model.addAttribute("addressList",list);
        return "addressList";
    }

    @GetMapping(value="/deleteAddress/{id}")
    public String deleteAddress(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        addressService.deleteAddress(id);
        redirectAttributes.addFlashAttribute("successmessage","Address a ete supprime avec succes!");
        return "redirect:/admin/addressList";
    }


/************************************* ADS **************************************************/
@GetMapping(value="/admin/ads")
public String adsForm(@ModelAttribute("ads") Ads ads){
    return "addAds";
}

@PostMapping(value="/admin/ads")
    public String addAds(@Valid @ModelAttribute("ads") Ads ads, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
    String role="";
    if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("ads",ads);
            return "addAds";
        }else {
            if(currentUser!=null && currentUser!=null)
                role = userService.findUserByRole(currentUser.getId());

            if (!role.equalsIgnoreCase("ADMIN") || currentUser.getActive()==0) {
                model.addAttribute("errormessage", currentUser.getUserName()+", You are not an Administrator!");
                return "addAds";
            }
            else {
                ads.setUser(currentUser);
             //   redirectAttributes.addFlashAttribute("successmessage", "Ads a ete enregistre avec succes!");
                adsService.saveAds(ads);
                return "redirect:/admin/adsList";
            }
        }
    }

    @PostMapping(value="/admin/editAds")
    public String ediAds(@Valid @ModelAttribute("ads") Ads ads, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("ads",ads);
            return "addAds";
        }else {

            redirectAttributes.addFlashAttribute("successmessage","Address a ete enregistre avec succes!");
            adsService.saveAds(ads);
            return "redirect:/admin/adsList";
        }
    }

    @GetMapping(value="/getAds/{id}")
    public String getAds(@PathVariable Long id, Model model) {
        Ads adsFind=adsService.findAds(id);
        model.addAttribute("ads",adsFind);
        return "editAds";
    }

    @GetMapping(value="/admin/adsList")
    public String AdsList(Model model){
        model.addAttribute("adsList",adsService.findAll());
        return "adsList";
    }

    @GetMapping(value="/deleteAds/{id}")
    public String deleteAds(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adsService.deleteAds(id);
        redirectAttributes.addFlashAttribute("successmessage","Ads a ete supprime avec succes!");
        return "redirect:/admin/adsList";
    }

    //****************************************************Payment**************************************
    @ModelAttribute(name="cardType")
    public Map<String, String > getCardType(Model model){
        Map<String, String> map=new HashMap<>();
        map.put("PAYPAL","PAYPAL");
        map.put("VISA","VISA");
        map.put("MASTERCARD","MASTERCARD");
        map.put("AMEX","AMEX");
        map.put("DISCOVER","DISCOVER");

        model.addAttribute("cardType",map);
        return map;
    }

    @GetMapping("/admin/paymentList")
    public String paymentList(Model model){

        List<Payment> addressList = paymentService.findAll();

        List<Payment> list = new ArrayList<>();
        for (Payment adr : addressList){
            if (adr.getCardType() == CardType.PAYPAL)
                adr.setCardValue("PAYPAL");
            else
            if (adr.getCardType() == CardType.VISA)
                adr.setCardValue("VISA");
            else
            if (adr.getCardType() == CardType.MASTERCARD)
                adr.setCardValue("MASTERCARD");
         else
            if (adr.getCardType() == CardType.AMEX)
                adr.setCardValue("AMEX");
            else
            if (adr.getCardType() == CardType.DISCOVER)
                adr.setCardValue("DISCOVER");

            list.add(adr);
        }

        model.addAttribute("paymentList",list);
        return "paymentList";
    }
    @GetMapping(value="/admin/payment")
    public String paymentForm(@ModelAttribute("payment") Payment payment, Model model){
        return "paymentForm";
    }

    @GetMapping(value="/admin/getPayment/{id}")
    public String getPayment(@PathVariable Long id, Model model) {
        Payment paymentFind=paymentService.findPayment(id);
        model.addAttribute("payment",paymentFind);
        return "paymentDetails";
    }

    @PostMapping(value="/admin/payment")
    public String addPayment(@Valid @ModelAttribute("payment") Payment payment, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        String role = "";
        if (result.hasErrors()) {
            model.addAttribute("errormessage", "Enregistrement echoue!");
            model.addAttribute("payment", payment);
            return "paymentForm";
        } else {
            if (currentUser != null)
                role = userService.findUserByRole(currentUser.getId());

            if (!role.equalsIgnoreCase("BUYER")) {
                model.addAttribute("errormessage", currentUser.getUserName() + ", You are not a buyer!");
                return "paymentForm";
            } else {
                Order ord = new Order(paymentService.savePayment(payment), items);
                ord.setBuyer(currentUser);
                // ord.setBillingAddress(currentUser);
                //ord.setBillingAddress(); ned to get the billing address of the buyer
                 Order order=orderService.saveOrder(ord);
                 List<OrderItem> orderItem=new ArrayList<>();
                 for(OrderItem di:items.values()){
                     orderItem.add(di);
                 }
                redirectAttributes.addFlashAttribute("order", order);
               redirectAttributes.addFlashAttribute("orderItem", orderItem);
            items.clear();
                return "redirect:/buyer/orderSuccess";
            }
        }
    }
    @GetMapping(value="/buyer/orderSuccess")
    public String orderSuccess() {

        return "orderSuccess";
    }

    @PostMapping(value="/admin/editPayment")
    public String editPayment(@ModelAttribute("payment") Payment payment, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errormessage","Enregistrement echoue!");
            model.addAttribute("payment",payment);
            return "paymentDetails";
        }else {
            redirectAttributes.addFlashAttribute("successmessage","payment a ete enregistre avec succes!");
            Payment paymentUpdate = paymentService.updatePayment(payment);
            return "redirect:/admin/paymentList";
        }
    }
    //****************************************************Payment**************************************

    //**************follow***************
    @GetMapping("/admin/follows")
    public String UserFollow(Model model){
        model.addAttribute("users",userService.findUserByRole());
        return "followUserlist";
    }

    @GetMapping(value="/followSeller/{id}")
    public String getFollowSeller(@PathVariable Long id, Model model) {
        User sellerFind=userService.findById(id);
        model.addAttribute("seller",sellerFind);
        User buyerFind=userService.findById(currentUser.getId());
        model.addAttribute("buyer",buyerFind);
      //  model.addAttribute("follow",new Follow());
        return "addFollow";
    }

    @GetMapping(value="/admin/addFollow")
    public String viwFollow(@RequestParam("buyer_id") Long buyer_id, @RequestParam("seller_id") Long seller_id, @ModelAttribute("follow") Follow follow, Model model)
    {
        Follow follow1=new Follow();
        follow1.setBuyer(userService.findById(buyer_id));
        follow1.setSeller(userService.findById(seller_id));
       // follow1.setDescription(description);
      //  followService.saveFollow(follow1);
        model.addAttribute("follow", followService.saveFollow(follow1));
        return "follows";
    }

//    @PostMapping(value="/admin/addFollow")
//    public String addFollow(@Valid @ModelAttribute("follow") Follow follow, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
//        if(result.hasErrors()) {
//            model.addAttribute("errormessage","Enregistrement echoue!");
//            model.addAttribute("follow",follow);
//            return "addFollow";
//        }else {
//            redirectAttributes.addFlashAttribute("successmessage","Payment a ete enregistre avec succes!");
//            followService.saveFollow(follow);
//            return "redirect:/admin/follows";
//        //}
//    }

}


