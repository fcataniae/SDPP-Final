import React from 'react';
import './SearchText.css';
import { Form, FormControl, Button } from 'react-bootstrap';

export default class SearchText extends React.Component {

    constructor(props){
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event){
        event.preventDefault();
        fetch('http://localhost:9000/rest/api/search', {
            method: 'get',
            headers: {'Content-Type':'application/json'}
        }).then(
            (res) => res.json()
        ).then(function(data){
            console.log(data);
        });
    };

    render(){
        return (
            <div className="search-component m-auto">
                <Form onSubmit={this.handleSubmit} className="form-inline">
                    <FormControl type="text" placeholder="Busqueda" className="text-md-center w-50 ml-auto" />
                    <Button type="submit" variant="outline-success" className="mr-lg-2 m-2 mr-auto " >Buscar</Button>
                </Form>
            </div>
        );
    }
}