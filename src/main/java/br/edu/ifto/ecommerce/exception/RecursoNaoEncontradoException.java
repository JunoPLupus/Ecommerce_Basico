package br.edu.ifto.ecommerce.exception;

/**
 * Lançada quando um recurso esperado não é encontrado (ex.: produto inexistente
 * ao manipular o carrinho). É unchecked para não poluir as assinaturas com
 * {@code throws Exception} genérico.
 */
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
