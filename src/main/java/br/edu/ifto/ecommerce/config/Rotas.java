package br.edu.ifto.ecommerce.config;

public final class Rotas {

    private Rotas() {}

    private static final String ADMIN = "admin";
    public static final String PRODUTOS = "produtos";
    public static final String CLIENTES = "clientes";
    private static final String VENDAS = "vendas";
    public static final String PEDIDOS = "pedidos";
    public static final String CARRINHO = "carrinho";

    public static final String LISTA = "/list";
    private static final String DETALHES = "/detalhes";
    public static final String CADASTRO = "/cadastro";
    public static final String INSERT = "/insert";
    public static final String SAVE = "/save";
    public static final String REDUCE = "/reduce";
    private static final String ADD = "/add";
    private static final String EDIT = "/edit";
    public static final String UPDATE = "/update";
    private static final String DELETE = "/delete";
    private static final String FISICA = "/fisica";
    private static final String JURIDICA = "/juridica";
    private static final String ID = "/{id}";

    public static final String ADMIN_PRODUTOS = ADMIN + "/" + PRODUTOS;
    public static final String ADMIN_CLIENTES = ADMIN + "/" + CLIENTES;
    public static final String ADMIN_VENDAS = ADMIN + "/" + VENDAS;

    public static final String ADD_ID = ADD + ID;
    public static final String INSERT_ID = INSERT + ID;
    public static final String REDUCE_ID = REDUCE + ID;
    public static final String DETALHES_ID = DETALHES + ID;
    public static final String EDIT_ID = EDIT + ID;
    public static final String DELETE_ID = DELETE + ID;

    public static final String SAVE_PF = SAVE + FISICA;
    public static final String SAVE_PJ = SAVE + JURIDICA;

    public static final String CADASTRO_CLIENTE = CLIENTES + CADASTRO;
}
