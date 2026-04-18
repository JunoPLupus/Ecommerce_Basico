package br.edu.ifto.ecommerce.utils;

import br.edu.ifto.ecommerce.model.record.BreadcrumbItem;

import java.util.List;

public final class BreadcrumbUtils {

    private BreadcrumbUtils() {}

    public static List<BreadcrumbItem> breadcrumb(BreadcrumbItem... items) {
        return List.of(items);
    }
}
