import Clock from "./DigitalClock";
import React from "react";

function setupStyles(backgroundImageUrl) {

    console.log("backgroundImageUrl: ", backgroundImageUrl);

    return {
        bgHome: {
            backgroundImage: `url("${backgroundImageUrl}")`,
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
    };
}

// Use PascalCase for the component name
function BackgroundStyle(props) {
    const styles = setupStyles(props.backgroundImageUrl);

    return (
        <div style={styles.bgHome}>
            <div style={styles.bgText}>
                {props.children}
            </div>

            <Clock /> {/* Use the Clock component */}
        </div>
    );
}

// Export with PascalCase
export default BackgroundStyle;