import React from "react";
import { useNavigate } from "react-router-dom";
import "./Product.scss";

const Product = ({ data, id }) => {
    const navigate = useNavigate();
    return (
        <div
            className="product-card"
            onClick={() => navigate("/product/" + id)}
        >
            
            <div className="prod-details">
                <span className="code">{data.code}</span>
                <span className="type">{data.type}</span>
                <span className="name">{data.name}</span>
                <span className="brand">{data.brand}</span>
                <span className="illustration">{data.illustration}</span>
                <span className="description">{data.description}</span>
                <span className="price">&#8377;{data.price}</span>
                <span className="quantity">{data.quantity}</span>
            
            </div>
            <div className="thumbnail">
                <img
                    src={
                        process.env.REACT_APP_STRIPE_APP_DEV_URL +
                        data.images.data[0].attributes.url
                    }
                />
            </div>
        </div>
    );
};

export default Product;
