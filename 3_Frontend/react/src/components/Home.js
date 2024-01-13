import { React, useState, useEffect } from "react";

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
    clock: {
        color: 'white', /* 白色字体以便在深色背景上看起来更清晰 */
        fontSize: '40px', /* 字体大小 */
        position: 'fixed', /* 固定位置，不随滚动条滚动 */
        bottom: '20px', /* 距离底部20px */
        right: '20px', /* 距离右侧20px */
        backgroundColor: 'rgba(0, 0, 0, 0.3)', /* 背景半透明黑色，增加对比度 */
        padding: '10px', /* 内边距 */
        borderRadius: '10px', /* 边角圆润 */
        zIndex: '1000' /* 高层次，确保不被其他元素遮挡 */
    }
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
                <div className="container">
                    <div className="d-grid gap-2 d-md-flex justify-content-md-center">
                        <a href="./catalog.html" className="btn btn-primary me-md-2" type="button">
                            Learn More
                        </a>
                    </div>
                </div>
            </div>

            {displayClock && (
                <div id="MyClockDisplay" style={homeStyles.clock}>
                    {time}
                </div>
            )}
        </div>
    );
}

export default Home;
