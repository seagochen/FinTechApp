import React from "react";
import {Link} from "react-router-dom";

// Import BackgroundStyle
import BackgroundStyle from "./BackgroundStyle";

function Error500() {
    // Define the URL for the background image
    const backgroundImageUrl = "/assets/imgs/error404.png";

    return (
        // Use BackgroundStyle with PascalCase
        <BackgroundStyle backgroundImageUrl={backgroundImageUrl}>
            <h1>505, Page Not Found!</h1>
            <p>Audentes Fortuna Iuvat!</p>
            <div className="container">
                <div className="d-grid gap-2 d-md-flex justify-content-md-center">
                    <Link className="btn btn-primary me-md-2" to="/" type="button">Back To Home</Link>
                </div>
            </div>
        </BackgroundStyle>
    );
}

export default Error500;
