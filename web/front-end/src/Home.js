import React from 'react';
import Button from 'react-bootstrap/Button';
import RegisterForm from './RegisterForm'
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

const divStyle = {
    margin: '5px',
};

function Index(){
    return <h2>index page</h2>;
}

class Home extends React.Component {
 
    handleClick(e) {
        console.log(e);

    }


    render() {
        return (
            <div>
                <Button id="register" style={divStyle} onClick={(e) => this.handleClick(e)}>Registreren</Button>
                <Button style={divStyle} onClick={(e) => this.handleClick(e)}>Bijleren</Button>

            </div >
        );
    }
}
<Route path="/registreren" exact component={Index} />

export default Home;
