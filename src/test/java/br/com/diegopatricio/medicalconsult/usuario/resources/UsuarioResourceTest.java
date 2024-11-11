package br.com.diegopatricio.medicalconsult.usuario.resources;

import br.com.diegopatricio.medicalconsult.usuario.domain.Usuario;
import br.com.diegopatricio.medicalconsult.usuario.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UsuarioResourceTest {

    @InjectMocks
    private UsuarioResource usuarioResource;

    @Mock
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNomeUsuario("Diego Patricio");
        usuario.setEmail("diego@teste.com");
    }

    @Test
    public void testCadastrarUsuario() {
        when(usuarioService.cadastrarUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioResource.cadastrarUsuario(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).cadastrarUsuario(any(Usuario.class));
    }

    @Test
    public void testBuscarUsuario() {
        when(usuarioService.buscarUsuario(1L)).thenReturn(usuario);
        ResponseEntity<Usuario> response = usuarioResource.buscarCliente(1L);
        //ResponseEntity<Usuario> response = usuarioResource.buscarCliente(2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).buscarUsuario(1L);
    }


    @Test
    public void testListarUsuario() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        when(usuarioService.listarUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> response = usuarioResource.listarClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
        verify(usuarioService, times(1)).listarUsuarios();
    }

    @Test
    public void testAtualizarUsuario() {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setIdUsuario(1L);
        usuarioAtualizado.setNomeUsuario("Diego Atualizado");
        usuarioAtualizado.setEmail("diego@atualizado.com");

        when(usuarioService.atualizarUsuario(anyLong(), any(Usuario.class))).thenReturn(usuarioAtualizado);

        ResponseEntity<Usuario> response = usuarioResource.atualizarCliente(1L, usuarioAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioAtualizado, response.getBody());
        verify(usuarioService, times(1)).atualizarUsuario(anyLong(), any(Usuario.class));
    }

    @Test
    public void testDeletarUsuario() {
        doNothing().when(usuarioService).deletarUsuario(anyLong());

        ResponseEntity<Void> response = usuarioResource.deletarUsuario(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).deletarUsuario(anyLong());
    }
}