import React from 'react';
import './SearchText.css';
import { Form, FormControl, Button } from 'react-bootstrap';
import { withRouter } from "react-router-dom";

class SearchText extends React.Component {

    criteria =[
        'extension',
        'tamaño',
        'fecha creacion',
        'nombre'
    ];

    constructor(props){
        super(props);
        this.state = {
            results: [],
            searchText: '',
        }
        console.log(this.criteria);
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.fetchData();
    }

    fetchData = () => {
        fetch(`http://localhost:9000/balancer-api/search/files?name=${this.state.searchText}`)
            .then(
                (res) => res.json()
            ).then(
                (data) => {
                    this.setState({results: data});
                    this.redirect();
                }
            );
    }

    redirect = () => {
        let results = this.state.results;
        this.props.history.push('/results', { results });
    }

    handleChange = (e) => {
        e.preventDefault();
        this.setState({searchText: e.target.value});
    }
    render(){
        return (
            <div className="search-component m-auto">
                <Form onSubmit={this.handleSubmit} className="form-inline">
                    <FormControl type="text" placeholder="Busqueda" className="text-md-center w-50 ml-auto mr-2"
                                 value={this.state.searchText} onChange={this.handleChange}
                    />
                    <Button type="submit" variant="outline-success" className="mr-auto "  >Buscar</Button>
                </Form>
            </div>
        );
    }
}

export default withRouter(SearchText);