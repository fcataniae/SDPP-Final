import {
  createReducer,
  on
} from '@ngrx/store';
import {ConfigurationComponent} from "../components/configuration/configuration.component";
import * as Action from "./app.actions";


export interface MenuState {
  enlaces : any[];
  brand : any;
  configuration: any;
  version: string;
  error: any
}

const menuInitialState: MenuState = {
  enlaces: [{view: 'Subir archivos', url: '/uploads'},
    {view: 'Compartidos', url: '/shareds'},
    {view: 'Busqueda avanzada', url: '/searchs'}],
  brand:  {url: '/', logoUrl: './assets/logo.jpg', logo: 'SD&PP'},
  configuration:{component: ConfigurationComponent},
  version: '',
  error: ''
};


export const _menuReducer = createReducer(menuInitialState,
  on(Action.getVersion, state => ({...state})),
  on(Action.successGetVersion, (state, action)=> ({...state, version: action.version})),
  on(Action.errorGetVersion, (state, action) => ({...state, error: action.error}))
);


export interface FileState{
  files: any[];
  error: any;
}

const fileInitialState : FileState = {
  files: [],
  error: ''
};

export const _fileReducer = createReducer(fileInitialState,
  on(Action.getAllFiles, state => ({...state})),
  on(Action.successGetAllFiles, (state, action)=> ({...state, files:([...action.files])})),
  on(Action.errorGetAllFiles, (state, action) => ({...state, error: action.error})),
  on(Action.removeFiles, state => ({...state, files: null, error: null}))
);

export interface ConfigState {
  config: any
}
const configInitialState: ConfigState = {
  config: null
};

export const _configReducer = createReducer(configInitialState,
  on(Action.getConfiguration, state => ({...state})),
  on(Action.successGetConfiguration, (state, action) => ({config: ({...action.config})})),
  on(Action.errorGetConfiguration, state => ({config: null})),
  on(Action.saveConfiguration, state => ({...state})),
  on(Action.successSaveConfiguration, (state, action) => ({config: ({...action.config})})),
  on(Action.errorSaveConfiguration, state => ({...state}))
);

export function menuReducer(state, action) {
  return _menuReducer(state, action);
}

export function fileReducer(state, action) {
  return _fileReducer(state, action);
}

export function configReducer(state, action) {
  return _configReducer(state, action);
}
