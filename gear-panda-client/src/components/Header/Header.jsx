import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { TbSearch } from "react-icons/tb";
import { CgShoppingCart } from "react-icons/cg";
import { AiOutlineHeart } from "react-icons/ai";
import "./Header.scss";
// import BannerImg1 from "../../../assets/banner-img1.png";
import Search from "./Search/Search";
import { Context } from "../../utils/context";
import Cart from "../Cart/Cart";

const Header = () => {
    const [scrolled, setScrolled] = useState(false);
    const [searchModal, setSearchModal] = useState(false);
    const navigate = useNavigate();
    const handleScroll = () => {
        const offset = window.scrollY;
        if (offset > 200) {
            setScrolled(true);
        } else {
            setScrolled(false);
        }
    };

    useEffect(() => {
        window.addEventListener("scroll", handleScroll);
    }, []);

    const { cartCount, showCart, setShowCart } = useContext(Context);

    return (
        <>
            <header
                className={`main-header ${scrolled ? "sticky-header" : ""}`}
            >
                <div className="header-content">
                    <div href="/" >
                    <img src="/logo_header.png" alt="GEARPANDA" className="left"  onClick={() => navigate("/")}/>
                    </div>
                    <div className="right">
                    <ul className="right">
                        <li onClick={() => navigate("/")}>Home</li>
                        <li onClick={() => navigate("/about")}>About</li>
                        <li>Categories</li>
                        <li>Warranty Policy</li>
                    </ul>
                        <TbSearch onClick={() => setSearchModal(true)} />
                        <AiOutlineHeart />
                        <span
                            className="cart-icon"
                            onClick={() => setShowCart(true)}
                        >
                            <CgShoppingCart />
                            {!!cartCount && <span>{cartCount}</span>}
                        </span>
                    </div>
                </div>
            </header>
            {searchModal && <Search setSearchModal={setSearchModal} />}
            {showCart && <Cart />}
        </>
    );
};

export default Header;
