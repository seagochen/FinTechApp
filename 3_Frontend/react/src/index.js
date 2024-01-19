import React from 'react'
import ReactDOM from 'react-dom'

import Home from "./components/Home";
import Error404 from "./components/Error404";
import Navbar from "./components/Navbar";
import {BrowserRouter, Route, Routes} from "react-router-dom";

// Define our routes here
function App() {
    return (
        <main>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/home" element={<Home />} />
                <Route path="*" element={<Error404 />} />
            </Routes>
        </main>
    );
}


const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <BrowserRouter>
        <Navbar />
        <App />
    </BrowserRouter>
)
