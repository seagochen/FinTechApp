import React from "react";

import Home from "./components/Home";
import About from "./components/About";
import Shop from "./components/Shop";
import Error from "./components/Error";
import Navbar from "./components/Navbar";
import {Route, Routes} from "react-router-dom";

function App() {
    return (
        <main>
            <Navbar />
            <Routes>
                <Route path="/" component={Home} exact />
                <Route path="/about" component={About} />
                <Route path="/shop" component={Shop} />
                <Route component={Error} />
            </Routes>
        </main>
    )
}

export default App;