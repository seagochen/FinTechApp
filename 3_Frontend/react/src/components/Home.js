import React from "react";

function Home() {
    return (
        <div class="bg-home">

             {/*White text floating in the middle of the screen */}
            <div class="bg-text"
                 style="color: white; font-size: 40px; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 2;">
                <h1>Hey, here you are!</h1>
                <p>NECESSITAS EST INGENII MATER!</p>

                {/* This is the button, in the middle of the screen */}
                <div class="containser">
                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">

                        {/* This will show a button here */}
                        <a href="./catalog.html" class="btn btn-primary me-md-2" type="button">Learn More</a>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Home;
