package br.edu.ifto.ecommerce.utils.constants;

public final class Diretorios {
    private Diretorios() {}

    private static final String HTML_ADMIN = "admin";
    private static final String HTML_CLIENTE = "cliente";
    private static final String HTML_AUTH = "auth";

    public static final String HTML_LOGIN = HTML_AUTH + "/login";

    private static final String HTML_ADMIN_PRODUTOS = HTML_ADMIN + "/produto";
    private static final String HTML_ADMIN_VENDAS = HTML_ADMIN + "/venda";
    private static final String HTML_ADMIN_CLIENTES = HTML_ADMIN + "/cliente";

    private static final String HTML_LISTA = "/list";
    private static final String HTML_DETAIL = "/detail";
    private static final String HTML_FORM = "/form";

    public static final String HTML_ADMIN_LISTA_PRODUTOS = HTML_ADMIN_PRODUTOS + HTML_LISTA;
    public static final String HTML_ADMIN_FORM_PRODUTOS = HTML_ADMIN_PRODUTOS + HTML_FORM;
    public static final String HTML_ADMIN_LISTA_VENDAS = HTML_ADMIN_VENDAS + HTML_LISTA;
    public static final String HTML_ADMIN_DETAIL_VENDAS =  HTML_ADMIN_VENDAS + HTML_DETAIL;
    public static final String HTML_ADMIN_LISTA_CLIENTES = HTML_ADMIN_CLIENTES + HTML_LISTA;
    public static final String HTML_ADMIN_DETAIL_CLIENTES = HTML_ADMIN_CLIENTES + HTML_DETAIL;

    private static final String HTML_CLIENTE_PEDIDO = HTML_CLIENTE + "/pedido";
    private static final String HTML_CLIENTE_ENDERECO = HTML_CLIENTE + "/endereco";

    public static final String HTML_CLIENTE_FORM = HTML_CLIENTE + HTML_FORM;
    public static final String HTML_CLIENTE_LISTA_PRODUTOS = HTML_CLIENTE + "/produto" + HTML_LISTA;
    public static final String HTML_CARRINHO = HTML_CLIENTE + "/carrinho/list";
    public static final String HTML_CLIENTE_LISTA_PEDIDOS = HTML_CLIENTE_PEDIDO + HTML_LISTA;
    public static final String HTML_CLIENTE_DETAIL_PEDIDO = HTML_CLIENTE_PEDIDO + HTML_DETAIL;
    public static final String HTML_CLIENTE_FINALIZAR_PEDIDO = HTML_CLIENTE_PEDIDO + "/finalizar";

    public static final String HTML_CLIENTE_LISTA_ENDERECOS = HTML_CLIENTE_ENDERECO + HTML_LISTA;
    public static final String HTML_CLIENTE_FORM_ENDERECO = HTML_CLIENTE_ENDERECO + HTML_FORM;
    public static final String HTML_CLIENTE_PERFIL = HTML_CLIENTE + "/perfil";
    public static final String HTML_CLIENTE_EDITAR_PERFIL = HTML_CLIENTE + "/editar";
}
