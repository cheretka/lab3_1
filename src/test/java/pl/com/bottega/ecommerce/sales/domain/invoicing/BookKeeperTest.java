package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.lang.reflect.InvocationHandler;

@ExtendWith(MockitoExtension.class)
class BookKeeperTest {


    public static final String SAMPLE_USERNAME = "Kowalski";
    @Mock
    private InvoiceFactory factory;
    @Mock
    private TaxPolicy taxPolicy;

    private BookKeeper keeper;
    private ClientData sampleClientData;
    private InvoiceRequest invoiceRequest;

    @BeforeEach
    void setUp() throws Exception {
        keeper = new BookKeeper(factory);
        sampleClientData = new ClientData(Id.generate(), SAMPLE_USERNAME);
        invoiceRequest = new InvoiceRequest(sampleClientData);
    }

    @Test
    void when_RequestOfInvoiceWithOneElement_Expect_ReturnInvoiceWithOneItem() {

        invoiceRequest.add(new RequestItemBuilder().build());

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
                .thenReturn(new Tax(Money.ZERO, "tax"));
        when(factory.create(any(ClientData.class)))
                .thenReturn(new Invoice(Id.generate(), sampleClientData));

        Invoice returnedInvoice = keeper.issuance(invoiceRequest, taxPolicy);
        assertTrue(nonNull(returnedInvoice));
        assertEquals(1, returnedInvoice.getItems().size());
    }

    @Test
    void when_RequestOfInvoiceWithZeroElement_Expect_ReturnInvoiceWithZeroItem() {

        when(factory.create(any(ClientData.class)))
                .thenReturn(new Invoice(Id.generate(), sampleClientData));

        Invoice returnedInvoice = keeper.issuance(invoiceRequest, taxPolicy);
        assertTrue(nonNull(returnedInvoice));
        assertEquals(0, returnedInvoice.getItems().size());
    }

    @Test
    void Expect_RequestProductsAndInvoiceProductsBeEquals() {

        invoiceRequest.add(new RequestItemBuilder().build());
        invoiceRequest.add(new RequestItemBuilder().build());

        Invoice expectedInvoice = new Invoice(Id.generate(), sampleClientData);

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
                .thenReturn(new Tax(Money.ZERO, "tax"));
        when(factory.create(sampleClientData))
                .thenReturn(expectedInvoice);

        Invoice returnedInvoice = keeper.issuance(invoiceRequest, taxPolicy);
        System.out.println(returnedInvoice.getItems());
        assertEquals(expectedInvoice.getItems(), returnedInvoice.getItems());
    }


    @Test
    void when_RequestOfInvoiceWithTwoElement_Expect_CallCalculateTaxTwice() {

        invoiceRequest.add(new RequestItemBuilder().build());
        invoiceRequest.add(new RequestItemBuilder().build());

        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
                .thenReturn(new Tax(Money.ZERO, "tax"));
        when(factory.create(any(ClientData.class)))
                .thenReturn(new Invoice(Id.generate(), sampleClientData));

        Invoice returnedInvoice = keeper.issuance(invoiceRequest, taxPolicy);
        assertTrue(nonNull(returnedInvoice));
        verify(taxPolicy, times(2)).calculateTax(any(ProductType.class), any(Money.class));
    }

    @Test
    void when_RequestOfInvoiceWithZeroElement_Expect_CallCalculateTaxZeroTimes() {

        when(factory.create(any(ClientData.class)))
                .thenReturn(new Invoice(Id.generate(), sampleClientData));

        Invoice returnedInvoice = keeper.issuance(invoiceRequest, taxPolicy);
        verifyNoInteractions(taxPolicy);
    }

    @Test
    void when_RequestOfInvoice_Expect_CallCreateOnes() {

        when(factory.create(any(ClientData.class)))
                .thenReturn(new Invoice(Id.generate(), sampleClientData));

        keeper.issuance(invoiceRequest, taxPolicy);
        verify(factory, times(1)).create(any(ClientData.class));
    }

}
