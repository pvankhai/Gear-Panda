import React from "react";

import "./Banner.scss";
import BannerImg from "../../../assets/banner-img.png";

const Banner = () => {
    return (
        <div className="hero-banner">
            <div className="content">
                <div className="text-content">
                    <h2>ROG Azoth</h2>
                    <p>
                    ROG Azoth mechanical gaming keyboard with 75 keys, gasket mount, three layers of damping foam, and top metal cover, customizable with exclusive ROG NX mechanical switches, easily replaceable, ROG keycaps, PBT keycap Doubleshot and lub and tri-mode connectivity with 2.4 GHz SpeedNova technology, OLED display, three-way controls, three tilt angles, and support for Mac.
                    </p>
                    <div className="ctas">
                        <div className="banner-cta">Read More</div>
                        <div className="banner-cta v2">Shop Now</div>
                    </div>
                </div>
                <img className="banner-img" src={BannerImg} />
            </div>
        </div>
    );
};

export default Banner;
