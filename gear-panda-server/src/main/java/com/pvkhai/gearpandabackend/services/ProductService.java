package com.pvkhai.gearpandabackend.services;

import com.pvkhai.gearpandabackend.models.Product;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ResponseEntity<ResponseObject> addNewProduct(Product product);

    ResponseEntity<ResponseObject> productsList(String keyword);
    ResponseEntity<ResponseObject> productsListNotKey();
    ResponseEntity<ResponseObject> uploadImageProduct(Long id, MultipartFile file) throws IOException;

    ResponseEntity<ResponseObject> updateImageProduct(Long id, MultipartFile file) throws IOException;
    ResponseEntity<ResponseObject> getProductById(Long id);

    ResponseEntity<ResponseObject> getProductByType(String type);

    ResponseEntity<ResponseObject> deleteProduct(Long id);

    ResponseEntity<ResponseObject> updateProduct(Long id, Product newProduct);

    List<Product> findByWithSorting(String field);

    Page<Product> findByWithPagination(int offset, int pageSize);

    ResponseEntity<ResponseObject> getProductByCode(Long id);

}
