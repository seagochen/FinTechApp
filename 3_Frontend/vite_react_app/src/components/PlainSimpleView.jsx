import DigitalClock from './DigitalClock.jsx';
import Image from 'react-bootstrap/Image';
import 'bootstrap/dist/css/bootstrap.min.css';
import * as PropTypes from "prop-types";
import {Component} from "react";

class PlainSimpleView extends Component {
    render() {
        let {imgUrl, showDigitalClock, children} = this.props;

        const styles = {
            // bgHome: {
            //     position: 'relative',
            //     height: '95vh',
            //     width: '100%',
            //     maxWidth: '100%',
            //     maxHeight: '100%',
            // },
            bgHome: {
                position: 'relative',  // Use a valid CSS position value
                height: '95vh',  // Use a string with a valid CSS unit
                width: '100%',  // Use a string with a valid CSS unit
                maxWidth: '100%',  // Use a string with a valid CSS unit
                maxHeight: '100%',  // Use a string with a valid CSS unit
            },
            bgImage: {
                position: 'absolute',
                objectFit: 'cover',
                width: '100%',
                height: '100%',
            },
            bgText: {
                color: 'white',
                fontSize: '40px',
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                zIndex: 2,
                backgroundColor: 'rgba(255, 255, 255, 0.1)',  // Add semi-transparent white background box
                padding: '20px',  // Add padding
                borderRadius: '10px',  // Add border radius
                width: '800px',  // Set fixed width
                height: '200px',  // Set fixed height
            },
        };

        return (
            <div style={styles.bgHome}>
                <Image src={imgUrl} style={styles.bgImage} />
                <div style={styles.bgText}>
                    {children}
                </div>
                {showDigitalClock && <DigitalClock />}
            </div>
        );
    }
}

PlainSimpleView.propTypes = {
    imgUrl: PropTypes.any,
    showDigitalClock: PropTypes.bool,  // should be a boolean
    children: PropTypes.node,  // should be a React node
};

export default PlainSimpleView;