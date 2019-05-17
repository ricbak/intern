import React from 'react';

import Notification from '../Notification'
import Card from 'react-bootstrap/Card';
import { Link } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner'


class Identify extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            identified: false,
            notificationText: "",
            busy: true,
        }
    }

    componentDidMount() {

        const axios = require('axios');

        axios.get('http://127.0.0.1:5000/identify')
            .then((response) => {
                const data = response.data;

                this.setState({
                    identified: true,
                    notificationText: data,
                    busy: false,
                })
            })
            .catch((error) => {
            })
            .then(() => {
            });
    }

    render() {
        const identified = this.state.identified;
        const busy = this.state.busy;

        const waiting = busy ?
            <div>
            <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
            </Spinner>
            <p>Kijk naar de camera</p>
            </div >
            : '';

        let display;


        if (identified) {
            display = <Notification name={this.state.personName} text={this.state.notificationText} />
        }

        return (
            <Card>
                <Card.Body className="text-center">
                    <div>
                        {waiting}
                        <Link to="/">
                            <Button variant="secondary">Terug</Button>
                        </Link>
                    </div>
                    <div>
                        {display}
                    </div>
                </Card.Body>
            </Card>
        );
    }
}

export default Identify;