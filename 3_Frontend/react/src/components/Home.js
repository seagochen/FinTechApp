import { React, useState, useEffect } from "react";
import Clock from "./DigitalClock";
import BackgroundStyle from "./BackgroundStyle";
import {Link} from "react-router-dom";

const homeStyles = {
    bgHome: {
        backgroundImage: 'url("/assets/imgs/background.png")',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        position: 'relative',
        height: '100%',
        width: '100%',
    },
    bgText: {
        color: 'white',
        fontSize: '40px',
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        zIndex: 2,
    },
}

function Home() {

    // Define the URL for the background image
    const backgroundImageUrl = "/assets/imgs/background.png";

    return (
        // Use BackgroundStyle with PascalCase
        <BackgroundStyle backgroundImageUrl={backgroundImageUrl}>
            <h1>Hey, here you are!</h1>
            <p>NECESSITAS EST INGENII MATER!</p>
            <div className="container">
                <div className="d-grid gap-2 d-md-flex justify-content-md-center">
                    <Link className="btn btn-primary me-md-2" to="/" type="button">Back To Home</Link>
                </div>
            </div>
        </BackgroundStyle>
    );
}

export default Home;
