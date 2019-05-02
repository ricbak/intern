import React from 'react';

import { Link } from "react-router-dom";

import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Spinner from 'react-bootstrap/Spinner'


class RegisterForm extends React.Component {

    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            name: null,
            loading: false,
            unique: true,
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
            name: name
        })
            .then((response) => {
                this.props.setRegister(name);
                console.log("response");
            })
            .catch((error) => {
                console.log(error);
                this.setState({ unique: false })})
            .then(() => {
                this.toggleLoading();
            });
    }

    render() {
        const loading = this.state.loading;
        const unique = this.state.unique;

        const notUnique = unique ? "" : <Form.Text className="text-muted">Deze naam bestaat al, kies een andere naam.</Form.Text>;

        const waiting = loading ?
            <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
            </Spinner> : '';

        return (
            <Form onSubmit={(e) => this.handleSubmit(e)}>
                <Card>
                    <Card.Body className="text-center">
                        <Form.Group controlId="name">
                            <Card.Title><Form.Label>Vul uw naam in om te registreren.</Form.Label></Card.Title>
                            <Form.Control type="text" placeholder="Uw naam" required onChange={(e) => this.handleChange(e)} />
                            {notUnique}
                        </Form.Group>
                        <Link to="/">
                            <Button variant="secondary">Annuleren</Button>
                        </Link>
                        <Button variant="primary" type="submit">Opslaan</Button>
                        
                    </Card.Body>
                    <Card.Footer className="text-center">{waiting}</Card.Footer>
                </Card>
            </Form>
        );
    }
}

export default RegisterForm;