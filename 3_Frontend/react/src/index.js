import React from 'react'
import ReactDOM from 'react-dom'

import Home from "./components/Home";
import About from "./components/About";
import Shop from "./components/Shop";
import Error from "./components/Error";
import Navbar from "./components/Navbar";
import {BrowserRouter, Route, Routes} from "react-router-dom";

function Navibar() {
    return (
        // <main>
        //     <Navbar />
        //     <Routes>
        //         <Route path="/" component={Home} exact />
        //         <Route path="/about" component={About} />
        //         <Route path="/shop" component={Shop} />
        //         <Route component={Error} />
        //     </Routes>
        // </main>

        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="container-fluid">
                <a className="navbar-brand" href="/">Orlando's Blog</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="nav nav-pills">
                        <li className="nav-item">
                            <a className="nav-link" routerLink="/home" routerLinkActive="active"
                               ariaCurrentWhenActive="page">Home</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" routerLink="/account-keeper" routerLinkActive="active"
                               ariaCurrentWhenActive="page">Account Keeper</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" routerLink="/ledger-keeper" routerLinkActive="active"
                               ariaCurrentWhenActive="page">Ledger Keeper</a>
                        </li>
                    </ul>
                </div>

            </div>
        </nav>
    )
}


const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <BrowserRouter>
        <Navibar/>
    </BrowserRouter>
)
