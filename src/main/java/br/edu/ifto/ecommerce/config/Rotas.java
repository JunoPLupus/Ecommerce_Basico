package br.edu.ifto.ecommerce.config;

public final class Rotas {

    private Rotas() {}

    public static final String ADMIN = "admin";
    public static final String PRODUTOS = "produtos";
    public static final String CLIENTES = "clientes";
    public static final String VENDAS = "vendas";
    public static final String LISTA = "/list";
    public static final String DETALHES = "/detalhes";
    public static final String CADASTRO = "/cadastro";
    public static final String INSERT = "/insert";
    public static final String SAVE = "/save";
    public static final String EDIT = "/edit";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String FISICA = "/fisica";
    public static final String JURIDICA = "/juridica";
    public static final String ID = "/{id}";

    public static final String ADMIN_PRODUTOS = ADMIN + "/" + PRODUTOS;
    public static final String ADMIN_CLIENTES = ADMIN + "/" + CLIENTES;
    public static final String ADMIN_VENDAS = ADMIN + "/" + VENDAS;
    public static final String DETALHES_ID = DETALHES + ID;
    public static final String SAVE_PF = SAVE + FISICA;
    public static final String SAVE_PJ = SAVE + JURIDICA;
    public static final String EDIT_ID = EDIT + ID;
    public static final String DELETE_ID = DELETE + ID;
}
