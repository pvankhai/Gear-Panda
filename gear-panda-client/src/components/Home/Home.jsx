import React, { useEffect, useContext, useState} from "react";
import "./Home.scss";
import Banner from "./Banner/Banner";
// import Category from "./Category/Category";
import Products from "../Products/Products";
import { fetchDataFromApi } from "../../utils/api";
import { Context } from "../../utils/context";
import axios from "axios";

const Home = () => {
    const { products, setProducts, categories, setCategories } =
        useContext(Context);
    useEffect(() => {
        getProducts();
        // getCategories();
    }, []);

    const getProducts = () => {
        fetchDataFromApi("/api/v1/products").then((res) => {
            console.log(res);
            console.log(res.data[0].name);
            setProducts(res.data[0].code);

        });
    };

    console.log(products)
    // const getCategories = () => {
    //     fetchDataFromApi("/api/categories?populate=*").then((res) => {
    //         setCategories(res);
    //     });
    // };

    // const [users, setUsers] =useState([])
    // useEffect(() =>{
    //     loadCategories();

    // },[])
    // const loadCategories = async()=>{
    //     const result = await axios.get("http://localhost:8080/api/v1/user");
    //     console.log(result);
    // }
    

    return (
        <div>
            <Banner />
            <div className="main-content">
                <div className="layout">
                    {/* <Category categories={categories} /> */}
                    <Products
                        headingText="Popular Products"
                        products={products}
                    />
                    <Products
                        headingText="Popular Products"
                        products={products}

                    />
                    
                    
                </div>
            </div>
        </div>
    );
};

export default Home;
