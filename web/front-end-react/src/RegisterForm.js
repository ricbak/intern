import React from 'react';

import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import Spinner from 'react-bootstrap/Spinner'
import { Link } from "react-router-dom";
import Button from 'react-bootstrap/Button';

class RegisterForm extends React.Component {

    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            name: null,
            loading: false,
            error: false,
            errorReason: "",
        };
    }

    componentDidMount() {
        this._isMounted = true;
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    toggleLoading() {
        if (this._isMounted) {
            this.setState({
                loading: this.state.loading ? false : true
            })
        }
    }

    handleChange(e) {
        console.log(e.target.value);

        this.setState({
            name: e.target.value,
            loading: false
        });
    }

    handleSubmit(e) {
        this.toggleLoading();
        e.preventDefault();

        var name = this.state.name;

        const axios = require('axios');

        console.log(name);

        axios.post('http://localhost:8080/person/register', {
            name
        })
            .then((response) => {
                this.props.setRegister(name);
                console.log("response");
            })
            .catch((er) => {
                console.log(er.response);
                var response = er.response.data
                this.setState({
                    unique: false,
                    error: true,
                    errorReason: response,
                })
            })
            .then(() => {
                this.toggleLoading();
            });
    }

    render() {
        const loading = this.state.loading;

        const waiting = loading ?
            <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
            </Spinner> : '';

        const errorStyle = this.state.error ? {} : { display: 'none' }

        const errorLine = (
            <Form.Text className="text-muted" >{this.state.errorReason}</Form.Text>
        );

        return (
            <Form onSubmit={(e) => this.handleSubmit(e)}>
                <Card>
                    <Card.Body className="text-center">
                        <Form.Group controlId="name">
                            <Card.Title><Form.Label>Vul uw naam in om te registreren.</Form.Label></Card.Title>
                            <Form.Control type="text" placeholder="Uw naam" required onChange={(e) => this.handleChange(e)} />
                            <div className="error-line" style={errorStyle}>
                                {errorLine}
                            </div>
                        </Form.Group>
                        <div>
                            <Link to="/">
                                <Button variant="secondary">Annuleren</Button>
                            </Link>
                            <Button variant="primary" type="submit">Opslaan</Button>
                        </div>
                    </Card.Body>
                    <Card.Footer className="text-center">{waiting}</Card.Footer>
                </Card>
            </Form>
        );
    }
}

export default RegisterForm;