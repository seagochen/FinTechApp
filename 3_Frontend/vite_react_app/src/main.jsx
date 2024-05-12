import React from 'react';
import ReactDOM from 'react-dom/client';
import { Route, Routes, BrowserRouter } from 'react-router-dom';
import { Container, Navbar, Nav, NavDropdown } from 'react-bootstrap';

import PlainSimpleView from './components/PlainSimpleView.jsx';

// 使用Bootstrap的组件
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

// 导入样式文件
import './css/main.css';
import 'bootstrap/dist/css/bootstrap.min.css';

// 导入背景图片
import background from './assets/background.png';


// 定义Home组件
function Home() {
    return (
        <PlainSimpleView imgUrl={background} showDigitalClock={true}>
            <h1>Welcome to Zero FinTech</h1>
            <p>NECESSITAS EST INGENII MATER!</p>
        </PlainSimpleView>
    )
}


// 定义About组件
function About() {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate('/');
    }

    return (
        <PlainSimpleView imgUrl={background} showDigitalClock={true}>
            <h1>Welcome to Zero FinTech</h1>
            <p>NECESSITAS EST INGENII MATER!</p>
            <Container>
                <Container className="d-grid gap-2 d-md-flex justify-content-md-center">
                    <Button className="btn btn-primary me-md-2" onClick={handleClick}>Back To Home</Button>
                </Container>
            </Container>
        </PlainSimpleView>
    )
}

// 定义导航栏
function NavPane() {
    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container fluid className="text-start">
                <Navbar.Brand href="/" className="me-auto">Zero FinTech</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ms-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="about">About</Nav.Link>
                        <NavDropdown title="Dropdown" id="basic-nav-dropdown">
                            <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}


// 定义路由信息
function AppRouter() {
    return (
        <BrowserRouter>
            <NavPane />
            <Container fluid style={{ marginTop: '20px' }}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="about" element={<About />} />
                </Routes>
            </Container>
        </BrowserRouter>
    );
}


// 渲染路由
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <AppRouter />
    </React.StrictMode>,
);