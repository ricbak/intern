import React from 'react';
import Card from 'react-bootstrap/Card';


function Notification(props) {
    return (
        <Card>
            <Card.Body className="text-center">
                <Card.Text>
                    {props.name} {props.text}
                </Card.Text>
            </Card.Body>
        </Card>
    )
}

export default Notification;