package com.amtraders.oms.config;

import com.amtraders.oms.entity.*;
import com.amtraders.oms.enums.OrderStatus;
import com.amtraders.oms.enums.Role;
import com.amtraders.oms.enums.UserStatus;
import com.amtraders.oms.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           SupplierRepository supplierRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           PurchaseRepository purchaseRepository,
                           OrderRepository orderRepository,
                           PaymentRepository paymentRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            System.out.println("DataInitializer: Database already contains data. Skipping initial seeding.");
            return;
        }

        System.out.println("DataInitializer: Seeding initial actual data into database...");

        // 1. Users
        User adminUser = User.builder()
                .username("admin")
                .email("admin@amtraders.com")
                .password(passwordEncoder.encode("admin123"))
                .phone("9876543210")
                .role(Role.ADMIN)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(adminUser);

        User userSarah = User.builder()
                .username("sarah")
                .email("sarah@garments.com")
                .password(passwordEncoder.encode("sarah123"))
                .phone("9876543211")
                .role(Role.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(userSarah);

        User userRobert = User.builder()
                .username("robert")
                .email("robert@apex.com")
                .password(passwordEncoder.encode("robert123"))
                .phone("9876543212")
                .role(Role.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(userRobert);

        User userDavid = User.builder()
                .username("david")
                .email("david@vanguard.com")
                .password(passwordEncoder.encode("david123"))
                .phone("9876543213")
                .role(Role.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(userDavid);

        // 2. Categories
        Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
        Category textiles = categoryRepository.save(Category.builder().name("Textiles & Fabrics").build());
        Category tools = categoryRepository.save(Category.builder().name("Industrial Tools").build());
        Category packaging = categoryRepository.save(Category.builder().name("Packaging Materials").build());
        Category chemicals = categoryRepository.save(Category.builder().name("Chemicals & Solvents").build());

        // 3. Suppliers
        Supplier supMicro = supplierRepository.save(Supplier.builder()
                .name("MicroTech Electronics Corp")
                .email("sales@microtech.com")
                .phone("+86 21 5888 1234")
                .address("Building B, High-Tech Industrial Zone, Shanghai")
                .build());

        Supplier supCotton = supplierRepository.save(Supplier.builder()
                .name("Heritage Cotton Mills")
                .email("info@heritagecotton.com")
                .phone("+91 422 234 5678")
                .address("Mill Road, Coimbatore, Tamil Nadu, India")
                .build());

        Supplier supTitanium = supplierRepository.save(Supplier.builder()
                .name("Titanium Forge & Tool Co.")
                .email("supply@titaniumforge.com")
                .phone("+49 211 4567 890")
                .address("Stahlstrasse 44, Düsseldorf, Germany")
                .build());

        // 4. Products
        Product prod1 = productRepository.save(Product.builder()
                .name("Ultra HD Industrial Monitor")
                .size("27 Inch")
                .buyPrice(new BigDecimal("210.00"))
                .sellingPrice(new BigDecimal("285.00"))
                .stockQuantity(42)
                .category(electronics)
                .supplier(supMicro)
                .build());

        Product prod2 = productRepository.save(Product.builder()
                .name("Organic Cotton Yarn Spool")
                .size("5 kg")
                .buyPrice(new BigDecimal("32.00"))
                .sellingPrice(new BigDecimal("45.50"))
                .stockQuantity(180)
                .category(textiles)
                .supplier(supCotton)
                .build());

        Product prod3 = productRepository.save(Product.builder()
                .name("Heavy-Duty Carbide Drill Bit Set")
                .size("24 Pcs")
                .buyPrice(new BigDecimal("85.00"))
                .sellingPrice(new BigDecimal("120.00"))
                .stockQuantity(18)
                .category(tools)
                .supplier(supTitanium)
                .build());

        Product prod4 = productRepository.save(Product.builder()
                .name("Double-Wall Corrugated Box")
                .size("50 Pk")
                .buyPrice(new BigDecimal("42.00"))
                .sellingPrice(new BigDecimal("65.00"))
                .stockQuantity(250)
                .category(packaging)
                .supplier(supMicro)
                .build());

        Product prod5 = productRepository.save(Product.builder()
                .name("Industrial Degreaser Solvent")
                .size("20 Liters")
                .buyPrice(new BigDecimal("60.00"))
                .sellingPrice(new BigDecimal("88.00"))
                .stockQuantity(35)
                .category(chemicals)
                .supplier(supTitanium)
                .build());

        // 5. Customers
        Customer custApex = customerRepository.save(Customer.builder()
                .shopName("Apex Global Traders Inc.")
                .ownerName("Robert Apex")
                .address("450 Industrial Parkway, Chicago, IL")
                .user(userRobert)
                .build());

        Customer custStarlight = customerRepository.save(Customer.builder()
                .shopName("Starlight Garments Ltd.")
                .ownerName("Sarah Jenkins")
                .address("12 Garment District Ave, New York, NY")
                .user(userSarah)
                .build());

        Customer custVanguard = customerRepository.save(Customer.builder()
                .shopName("Vanguard Tech Solutions")
                .ownerName("David Tech")
                .address("88 Innovation Hub, Austin, TX")
                .user(userDavid)
                .build());

        // 6. Purchases & Items
        Purchase purchase1 = Purchase.builder()
                .supplier(supMicro)
                .purchaseDate(LocalDateTime.now().minusDays(10))
                .totalAmount(new BigDecimal("5250.00"))
                .items(new ArrayList<>())
                .build();

        PurchaseItem pItem1 = PurchaseItem.builder()
                .purchase(purchase1)
                .product(prod1)
                .quantity(25)
                .buyPrice(new BigDecimal("210.00"))
                .subtotal(new BigDecimal("5250.00"))
                .build();
        purchase1.getItems().add(pItem1);
        purchaseRepository.save(purchase1);

        Purchase purchase2 = Purchase.builder()
                .supplier(supCotton)
                .purchaseDate(LocalDateTime.now().minusDays(5))
                .totalAmount(new BigDecimal("3200.00"))
                .items(new ArrayList<>())
                .build();

        PurchaseItem pItem2 = PurchaseItem.builder()
                .purchase(purchase2)
                .product(prod2)
                .quantity(100)
                .buyPrice(new BigDecimal("32.00"))
                .subtotal(new BigDecimal("3200.00"))
                .build();
        purchase2.getItems().add(pItem2);
        purchaseRepository.save(purchase2);

        // 7. Orders & Items
        Order order1 = Order.builder()
                .customer(custApex)
                .orderDate(LocalDateTime.now().minusDays(3))
                .totalAmount(new BigDecimal("910.00"))
                .orderStatus(OrderStatus.DELIVERED)
                .items(new ArrayList<>())
                .build();

        OrderItem oItem1 = OrderItem.builder()
                .order(order1)
                .product(prod1)
                .quantity(2)
                .sellingPrice(new BigDecimal("285.00"))
                .subtotal(new BigDecimal("570.00"))
                .build();

        OrderItem oItem2 = OrderItem.builder()
                .order(order1)
                .product(prod3)
                .quantity(1)
                .sellingPrice(new BigDecimal("340.00"))
                .subtotal(new BigDecimal("340.00"))
                .build();

        order1.getItems().add(oItem1);
        order1.getItems().add(oItem2);
        Order savedOrder1 = orderRepository.save(order1);

        Order order2 = Order.builder()
                .customer(custStarlight)
                .orderDate(LocalDateTime.now().minusDays(1))
                .totalAmount(new BigDecimal("1001.00"))
                .orderStatus(OrderStatus.APPROVED)
                .items(new ArrayList<>())
                .build();

        OrderItem oItem3 = OrderItem.builder()
                .order(order2)
                .product(prod2)
                .quantity(22)
                .sellingPrice(new BigDecimal("45.50"))
                .subtotal(new BigDecimal("1001.00"))
                .build();

        order2.getItems().add(oItem3);
        Order savedOrder2 = orderRepository.save(order2);

        // 8. Payments
        paymentRepository.save(Payment.builder()
                .order(savedOrder1)
                .amount(new BigDecimal("910.00"))
                .paymentMethod("BANK_TRANSFER")
                .paymentDate(LocalDateTime.now().minusDays(3))
                .status("COMPLETED")
                .build());

        paymentRepository.save(Payment.builder()
                .order(savedOrder2)
                .amount(new BigDecimal("500.00"))
                .paymentMethod("CASH")
                .paymentDate(LocalDateTime.now().minusDays(1))
                .status("PARTIAL")
                .build());

        System.out.println("DataInitializer: Initial data seeded successfully!");
    }
}
