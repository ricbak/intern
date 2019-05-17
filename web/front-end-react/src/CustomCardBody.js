import React from 'react';
import Card from 'react-bootstrap/Card';
import { Link } from "react-router-dom";
import Button from 'react-bootstrap/Button';

class CustomCardBody extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            requestUrl: "",
            showInput: true,
        }
    }


    handleRequest(){
        this.setState({
            showInput: false,
        })
        console.log("handleRequest");
    }


    render() {
        let textBody = <TextBody text={this.props.text} />;
        let input;

        if (this.state.showInput) {
            input = <InputBody  handleRequest = {this.handleRequest}/>;
        }

        return (
            <div>
                {textBody}
                {input}
            </div>

        );
    }
}
export default CustomCardBody

function InputBody(props) {
    return (<div>
        <Link to="/">
            <Button variant="secondary">Annuleren</Button>
        </Link>
        <Button variant="primary" type="submit" onClick={props.handleRequest}>Opslaan</Button>
    </div>
    )
}

function TextBody(props) {
    return (
        <Card.Text>
            {props.text}
        </Card.Text>
    )
}