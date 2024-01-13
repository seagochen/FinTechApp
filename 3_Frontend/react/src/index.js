import React from 'react'
import ReactDOM from 'react-dom'

import Home from "./components/Home";
import About from "./components/About";
import Shop from "./components/Shop";
import Error404 from "./components/Error404";
import Navbar from "./components/Navbar";
import {BrowserRouter, Route, Routes} from "react-router-dom";

// Define our routes here
function App() {
    return (
        <main>
            <Navbar />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/home" element={<Home />} />
                <Route path="/about" element={<About />} />
                <Route path="/shop" element={<Shop />} />
                <Route path="*" element={<Error404 />} />
            </Routes>
        </main>
    );
}


const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
)
