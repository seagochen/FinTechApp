import { React, useState, useEffect } from "react";
import Clock from "./DigitalClock";

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
    const [time, setTime] = useState(""); // 用于显示时间的状态
    const [displayClock, setDisplayClock] = useState(true); // 控制时钟是否显示的状态

    useEffect(() => {
        // 初始化时钟显示
        updateClockDisplay(window.innerWidth);

        // 开始显示时间
        showTime();

        // 当窗口大小发生变化时，更新时钟显示
        const handleResize = () => {
            updateClockDisplay(window.innerWidth);
        };

        window.addEventListener("resize", handleResize);

        return () => {
            // 清除resize事件监听
            window.removeEventListener("resize", handleResize);
        };
    }, []);

    // 更新时钟显示的方法
    const updateClockDisplay = (width) => {
        setDisplayClock(width >= 768);
    };

    // 时钟逻辑
    const showTime = () => {
        setInterval(() => {
            const date = new Date();
            let hours = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();

            hours = hours < 10 ? "0" + hours : hours;
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            const currentTime = `${hours}:${minutes}:${seconds}`;

            // 使用setTime更新时间
            setTime(currentTime);
        }, 1000);
    };

    return (
        <div style={homeStyles.bgHome}>
            <div style={homeStyles.bgText}>
                <h1>Hey, here you are!</h1>
                <p>NECESSITAS EST INGENII MATER!</p>
                {/*<div className="container">*/}
                {/*    <div className="d-grid gap-2 d-md-flex justify-content-md-center">*/}
                {/*        <a href="./catalog.html" className="btn btn-primary me-md-2" type="button">*/}
                {/*            Learn More*/}
                {/*        </a>*/}
                {/*    </div>*/}
                {/*</div>*/}
            </div>

            <Clock /> {/* Use the Clock component */}
        </div>
    );
}

export default Home;
