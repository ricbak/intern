import React from 'react';

import LearnForm from '../LearnForm'
import Notification from '../Notification'
import { Link } from "react-router-dom";
import Button from 'react-bootstrap/Button';

class Learn extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            personName: "",
            choseName: false,
            notificationText: "",
        }

        this.setName = this.setName.bind(this);
    }

    setName(name, response) {
        console.log(name);
        console.log(response);

        this.setState({
            personName: name,
            choseName: true,
            notificationText: response,
        })
    }

    render() {
        const choseName = this.state.choseName;

        let display;

        if (choseName) {
            display =
            <div>
                <div>
                    <Link to="/">
                        <Button variant="secondary">Begin scherm</Button>
                    </Link>
                </div>
                <Notification name={this.state.personName} text={this.state.notificationText} />
                </div>

        } else {
            display = <LearnForm setName={(name, azureId) => this.setName(name, azureId)} />
        }

        return (
            display
        );
    }
}

export default Learn;