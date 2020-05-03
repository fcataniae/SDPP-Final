import React from 'react';
import './SearchText.css';
import {Button, Form, FormControl} from 'react-bootstrap';
import {withRouter} from 'react-router-dom';
import logo from "../../logo.svg";


class SearchText extends React.Component {

    criteria =[
        { placeholder: 'Extension', name: 'extension', value: ''},
        { placeholder: 'Mayor a|Menor a', name: 'sizeFiter', value: ''},
        { placeholder: 'Tamaño', name: 'size', value: 0},
        { placeholder: 'Fecha Creacion', name: 'createdTime', value: ''},
        { placeholder: 'Fecha Modificacion', name: 'modifiedTime', value: ''},
        { placeholder: 'Autor', name: 'author', value: ''},
    ];

    constructor(props){
        super(props);
        this.state = {
            results: [],
            criteria: this.criteria,
            searchText: '',
            align: 'ml-auto',
        };

        if(this.isNotNullBlankOrUndefined(this.props.fix) && this.props.fix === 'left'){
            this.state.align =  'ml-2';
        }
    }

    isNotNullBlankOrUndefined(fix) {

        return fix != null && fix && fix.trim() !== '';
    }


    handleSubmit = (event) => {
        event.preventDefault();
        this.fetchData();
    }

    fetchData = () => {
        let url = new URL('http://localhost:9000/balancer-api/search/files');
        url.searchParams.append('name', this.state.searchText);
        this.state.criteria.forEach(o => url.searchParams.append(o.name, o.value));
        fetch(url)
            .then(
                (res) => res.json()
            ).then(
                (data) => {
                    this.setState({results: data});
                    this.redirect();
                }
            ).catch((reason) => {
                console.log(reason);
        });
    }

    redirect = () => {
        let results = this.state.results;
        console.log(this.state.criteria);
        this.props.history.push('/results', { results });
    }

    handleChange = (e) => {
        e.preventDefault();
        this.setState({searchText: e.target.value});
    }
    render(){
        return (
            <div className='search-component m-auto'>
                <Form onSubmit={this.handleSubmit} className='form-inline'>
                    <Logo align={this.props.fix}/>
                    <FormControl type='text' placeholder='Busqueda' className={`text-md-left w-50 ${this.state.align} mr-2`}
                                 value={this.state.searchText} onChange={this.handleChange}
                    />
                    <Button type='submit' variant='outline-success' className='mr-auto '  >Buscar</Button>
                </Form>
                <AdvancedSearch criteria={this.state.criteria} align={this.state.align} />
                <ShowResults results={this.props.location}/>
            </div>
        );
    }
}

export const Logo = ({align}) => {

    let show = align === 'left';
    return show ? <a href='/'>
        <img className='left-logo' src={logo} alt="logo"/>
    </a> : null;
}

export const ShowResults = ({results}) => {
    console.log(results);
    let show = results && results.state && results.state.results;
    return show ?
        <div className='ml-2 MuiTypography-body2'>Mostrando {results.state.results.length || 0} resultados</div> : null;
}

export const AdvancedSearch = ({criteria, align}) => {
    return <div className={align}>criteria</div>;
}

export default withRouter(SearchText);