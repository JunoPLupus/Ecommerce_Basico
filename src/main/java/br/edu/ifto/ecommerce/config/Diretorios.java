package br.edu.ifto.ecommerce.config;

public final class Diretorios {
    private Diretorios() {}

    private static final String HTML_ADMIN_PRODUTOS = "admin/produto";
    private static final String HTML_ADMIN_VENDAS = "admin/venda";
    private static final String HTML_ADMIN_CLIENTES = "admin/cliente";

    private static final String HTML_CLIENTE = "cliente";
    private static final String HTML_LISTA = "/list";
    private static final String HTML_DETAIL = "/detail";
    private static final String HTML_FORM = "/form";

    public static final String HTML_ADMIN_LISTA_PRODUTOS = HTML_ADMIN_PRODUTOS + HTML_LISTA;
    public static final String HTML_ADMIN_FORM_PRODUTOS = HTML_ADMIN_PRODUTOS + HTML_FORM;
    public static final String HTML_ADMIN_LISTA_VENDAS = HTML_ADMIN_VENDAS + HTML_LISTA;
    public static final String HTML_ADMIN_DETAIL_VENDAS =  HTML_ADMIN_VENDAS + HTML_DETAIL;
    public static final String HTML_ADMIN_LISTA_CLIENTES = HTML_ADMIN_CLIENTES + HTML_LISTA;
    public static final String HTML_ADMIN_DETAIL_CLIENTES = HTML_ADMIN_CLIENTES + HTML_DETAIL;

    public static final String HTML_CLIENTE_FORM = HTML_CLIENTE + HTML_FORM;
    public static final String HTML_CLIENTE_LISTA_PRODUTOS = HTML_CLIENTE + "/produto/list";
}
