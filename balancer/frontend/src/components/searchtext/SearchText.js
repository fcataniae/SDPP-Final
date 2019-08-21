import React from 'react';
import './SearchText.css';
import { Form, FormControl, Button } from 'react-bootstrap';
import { withRouter } from "react-router-dom";

class SearchText extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            results: []
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.fetchData();
    }

    fetchData = () => {
        fetch('http://localhost:9000/rest/api/search')
            .then(
                (res) => res.json()
            ).then(
                (data) =>
                this.setState({results: data})
            );
    }

    componentDidUpdate = () => {
        let results = this.state.results;
        this.props.history.push('/results', { results });
    }

    render(){
        return (
            <div className="search-component m-auto">
                <Form onSubmit={this.handleSubmit} className="form-inline">
                    <FormControl type="text" placeholder="Busqueda" className="text-md-center w-50 ml-auto mr-2" />
                    <Button type="submit" variant="outline-success" className="mr-auto " >Buscar</Button>
                </Form>
            </div>
        );
    }
}

export default withRouter(SearchText);