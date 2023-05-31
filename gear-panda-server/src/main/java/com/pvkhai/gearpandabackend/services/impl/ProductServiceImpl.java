package com.pvkhai.gearpandabackend.services.impl;

import com.pvkhai.gearpandabackend.exceptions.FileUploadExceptionAdvice;
import com.pvkhai.gearpandabackend.models.Images;
import com.pvkhai.gearpandabackend.models.Product;
import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.models.Roles;
import com.pvkhai.gearpandabackend.repositories.ProductRepository;
import com.pvkhai.gearpandabackend.services.ImagesService;
import com.pvkhai.gearpandabackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImagesService imagesService;


    /**
     * Add product new:
     * Case 1: coincide with existing products -> update quantity
     * Case 2: not coincide with existing products -> add new Product
     *
     * @param newProduct
     * @return
     */
    public ResponseEntity<ResponseObject> addNewProduct(Product newProduct) {
        Product product = productRepository.findByName(newProduct.getName().trim());
        System.out.printf(String.valueOf(product));
        if (product != null) {
            productRepository.findByName(newProduct.getName()).setQuantity(product.getQuantity() + newProduct.getQuantity());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Insert Product successfully!", productRepository.save(product))
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Insert Product successfully!", productRepository.save(newProduct))
            );
        }
    }


    /**
     *
     * @param id
     * @param file
     * @return
     * @throws IOException
     */
    public ResponseEntity<ResponseObject> uploadImageProduct(Long id, MultipartFile file) throws IOException {
        String fileName = id + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Images image = new Images(fileName, file.getContentType(), file.getBytes());

        Optional<Product> updateProduct = productRepository.findById(id)
                .map(product -> {
                    Collection<Images> arr;
                    arr = product.getImages();
                    arr.add(image);
                    product.setImages(arr);
                    return productRepository.save(product);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update Product successfully!", updateProduct)
        );
    }


    /**
     * Get all products with keyword (Search)
     *
     * @param keyword
     * @return
     */
    public ResponseEntity<ResponseObject> productsList(String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Query product successfully", productRepository.findAll(keyword))
        );
    }

    public ResponseEntity<ResponseObject> productsListNotKey() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Query product successfully", productRepository.findAll())
        );
    }


    /**
     * Get product by ID
     *
     * @param id
     * @return
     */
    public ResponseEntity<ResponseObject> getProductById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Query product successfully", foundProduct)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Could not found product with Id = " + id, "N/A")
                );
    }


    /**
     * Get product by Type(Keyboard, Mouse,...)
     *
     * @param type
     * @return
     */
    public ResponseEntity<ResponseObject> getProductByType(String type) {
        List<Product> foundProducts = productRepository.findByType(type.trim());
        return foundProducts.size() > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Query product successfully", foundProducts)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Could not found product with Type = " + type, "N/A")
                );
    }


    /**
     * Update info product
     *
     * @param id
     * @param newProduct
     * @return
     */
    public ResponseEntity<ResponseObject> updateProduct(Long id, Product newProduct) {
        Product updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setCode(newProduct.getCode());
                    product.setType(newProduct.getType());
                    product.setName(newProduct.getName());
                    product.setBrand(newProduct.getBrand());
                    product.setIllustration(newProduct.getIllustration());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setQuantity(newProduct.getQuantity());
                    return productRepository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update Product successfully!", updateProduct)
        );
    }


    /**
     * @param id
     * @param file
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = { FileUploadExceptionAdvice.class })
    public ResponseEntity<ResponseObject> updateImageProduct(Long id, MultipartFile file) throws IOException {

        if (imagesService.isImageFile(file)) {
            String fileName = id + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Images image = new Images(fileName, file.getContentType(), file.getBytes());
            Optional<Product> updateProduct = productRepository.findById(id)
                    .map(product -> {
                        product.setImages(new ArrayList<>(Arrays.asList(image)));
                        return productRepository.save(product);
                    });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Update Product successfully!", updateProduct)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("FAILED", "The file is not in the correct format!", "NA")
        );

    }


    /**
     * Delete product based on ID
     *
     * @param id
     * @return
     */
    public ResponseEntity<ResponseObject> deleteProduct(Long id) {
        Boolean exists = productRepository.existsById(id);
        if (exists) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete Product successfully!", "N/A")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Cannot found product!", "N/A")
            );
        }
    }


    /**
     * Sorting all product
     *
     * @param field
     * @return
     */
    public List<Product> findByWithSorting(String field) {
        return field != null ?
                productRepository.findAll(Sort.by(Sort.Direction.ASC, field)) :
                productRepository.findAll();
    }


    /**
     * Pagination
     *
     * @param offset
     * @param pageSize
     * @return
     */
    public Page<Product> findByWithPagination(int offset, int pageSize) {
        return productRepository.findAll(PageRequest.of(offset, pageSize));
    }

    @Override
    public ResponseEntity<ResponseObject> getProductByCode(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Query product successfully", foundProduct)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Could not found product with Id = " + id, "N/A")
                );
    }

}
