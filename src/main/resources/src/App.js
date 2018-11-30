import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import TextField from '@material-ui/core/TextField';
import Select from '@material-ui/core/Select';
import PropTypes from 'prop-types';
import ReactDOM from 'react-dom';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Button from '@material-ui/core/Button';
import * as fetch from "node-fetch";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formControl: {
        margin: theme.spacing.unit,
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing.unit * 2,
    },
});

class App extends Component {

    state = {
        operation: '',
        name: '',
        newurl: '',
    };

    update(val){
      this.state.operation = val;
    }

    update2(val){
      this.state.name = val;
    }

    callFetch(){
        const body = { a: 1 };

        fetch('http://localhost:8080/convolve', {
            method: 'post',
            body:    JSON.stringify({kernel: this.state.operation, url: this.state.name}),
            headers: { 'Content-Type': 'application/json' },
        })

            .then(res => var buffer = res.arrayBuffer()  new Buffer(buffer, 'binary').toString(CONVERTED_FORMAT);
            .then(json => console.log(json));
}

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={'https://www.thefutonshop.com/media/catalog/product/cache/1/image/650x433/9df78eab33525d08d6e5fb8d27136e95/S/o/Solid_White_Futon_Cover.jpg'}/>
          {/*<img src={logo} className="App-logo" alt="logo" />*/}
          <p>
            enter your link here:
          </p>
          <TextFields  custom2={this.update2.bind(this)} classes={{textField: 'test', container: 'test2' }} height={'500'} width={'700'}/>
          <SimpleSelect custom={this.update.bind(this)}/>
            <Button onClick={this.callFetch.bind(this)} className={'button'}>ACTIVATE</Button>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
      </div>
    );
  }
}

 class TextFields extends React.Component {
    state = {
        name: 'enter url',
    };

    handleChange = name => event => {
        this.setState({
            [name]: event.target.value,
        });
        this.props.custom2(event.target.value);
    };

    render() {
        const { classes } = this.props;

        return (
            <form className={classes.container} noValidate autoComplete="off">
                <TextField
                    id="standard-name"
                    className={classes.textField}
                    value={this.state.name}
                    onChange={this.handleChange('name')}
                    margin="normal"
                />
            </form>
        );
    }
}

class SimpleSelect extends React.Component {
    state = {
        operation: '',
        name: 'hai',
        labelWidth: 0,
    };


    handleSelect = event => {
      this.setState({ operation: event.target.value})
        this.props.custom(event.target.value);
    }


    render() {
        // const { classes } = this.props;

        return (
            <form className={'random'} autoComplete="off">
                <FormControl className={'form control'}>
                    <InputLabel htmlFor="age-simple">operation</InputLabel>
                    <Select
                        value={this.state.operation}
                        onChange={this.handleSelect}
                        inputProps={{
                            name: 'OPERATION',
                            id: 'age-simple',
                        }}
                    >
                        <MenuItem value="">
                            <em>value</em>
                        </MenuItem>
                        <MenuItem value={'BLUR'}>BLUR</MenuItem>
                        <MenuItem value={'IDENTITY'}>IDENTITY</MenuItem>
                        <MenuItem value={'SHARP'}>SHARP</MenuItem>
                        <MenuItem value={'EDGE'}>EDGE</MenuItem>
                    </Select>
                </FormControl>
            </form>
        )
  }
}


;

export default App;