import React from 'react';

import RegisterForm from '../RegisterForm'
import RegisterCompleted from '../RegisterCompleted'
import Home from './Home';


class Register extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            name: '',
            isRegistered: false,
        }

        this.setRegister = this.setRegister.bind(this);

    }

    setRegister(e) {
        this.setState({
            name: e,
            isRegistered: true
        });
    }

    render() {
        const name = this.state.name;
        const isRegistered = this.state.isRegistered;

        let content;

        if (!isRegistered) {
            content = <RegisterForm setRegister={this.setRegister} />
        } else {
            content = <Home/>
        }

        return (
            content
        );
    }
}

export default Register;