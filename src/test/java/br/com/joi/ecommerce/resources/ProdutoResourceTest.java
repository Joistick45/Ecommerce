package br.com.joi.ecommerce.resources;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ProdutoResourceTest {

    @Autowired
    private MockMvc mockMvc;
	
	@Test
    public void deveriaDevolver201AoCriarProdutoComCategoria() throws Exception {
        URI uri = new URI("/produtos");
        String json = "{\"nome\": \"Teste\",\"preco\": 10000.0,\"categorias\": [12,13]}";

        mockMvc
        .perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers
                .status()
                .is(201));
    }
	
}
