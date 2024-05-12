// eslint-disable-next-line no-unused-vars
import { useEffect, useState } from 'react';

const Clock = () => {
    const [time, setTime] = useState("");
    const [displayClock, setDisplayClock] = useState(true);

    useEffect(() => {
        const updateClockDisplay = (width) => {
            setDisplayClock(width >= 768);
        };

        updateClockDisplay(window.innerWidth);

        const showTime = () => {
            setInterval(() => {
                const date = new Date();
                let hours = date.getHours();
                let minutes = date.getMinutes();
                let seconds = date.getSeconds();

                hours = hours < 10 ? "0" + hours : hours;
                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                setTime(`${hours}:${minutes}:${seconds}`);
            }, 1000);
        };

        showTime();

        const handleResize = () => updateClockDisplay(window.innerWidth);
        window.addEventListener('resize', handleResize);

        return () => window.removeEventListener('resize', handleResize);
    }, []);

    const clockStyle = {
        color: 'white',
        fontSize: '40px',
        position: 'fixed',
        bottom: '20px',
        right: '20px',
        backgroundColor: 'rgba(0, 0, 0, 0.3)',
        padding: '10px',
        borderRadius: '10px',
        zIndex: '1000'
    };

    return displayClock ? (
        <div id="MyClockDisplay" style={clockStyle}>
            {time}
        </div>
    ) : null;
};

export default Clock;