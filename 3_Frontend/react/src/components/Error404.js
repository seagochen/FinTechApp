import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import Clock from "./DigitalClock";


const errorStyles = {
    bgHome: {
        backgroundImage: 'url("/assets/imgs/error404.png")',
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

function Error404() {
    return (
        <div style={errorStyles.bgHome}>
            <div style={errorStyles.bgText}>
                <h1>404, Page Not Found!</h1>
                <p>Audentes Fortuna Iuvat!</p>
                <div className="container">
                    <div className="d-grid gap-2 d-md-flex justify-content-md-center">
                        <Link className="btn btn-primary me-md-2" to="/" type="button">Back To Home</Link>
                    </div>
                </div>
            </div>

            <Clock /> {/* Use the Clock component */}
        </div>
    );
};

export default Error404;
