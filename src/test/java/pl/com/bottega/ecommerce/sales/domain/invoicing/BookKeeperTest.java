package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductDataBuilder;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.lang.reflect.InvocationHandler;

@ExtendWith(MockitoExtension.class)
class BookKeeperTest {


    @Mock
    private InvoiceFactory factory;
    @Mock
    private TaxPolicy taxPolicy;

    private BookKeeper keeper;

    @BeforeEach
    void setUp() throws Exception {
        keeper = new BookKeeper(factory);
    }


    @Test
    void when_RequestOfInvoiceWithOneElement_Expect_ReturnInvoiceWithOneItem() {
//        Test case 1
        ClientData sampleClientData = new ClientData(Id.generate(), "Kowalski");
        InvoiceRequest invoiceRequest = new InvoiceRequest(sampleClientData);
        invoiceRequest.add(new RequestItemBuilder().setQuantity(1).build());

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class))).thenReturn(new Tax(Money.ZERO, "tax"));
        when(factory.create(sampleClientData)).thenReturn(new Invoice(Id.generate(), sampleClientData));

        Invoice returnedInvoice = keeper.issuance(invoiceRequest, taxPolicy);
        assertTrue(nonNull(returnedInvoice));
        assertEquals(1, returnedInvoice.getItems().size());
    }

}
