import {createAction, props} from '@ngrx/store';

export const getVersion = createAction(
  '[Get Version]'
);

export const succesVersion = createAction(
  '[Success Version]',
        props<{version: string}>()

);

export const errorVersion = createAction(
  '[Error Version]',
  props<{version: string}>()
)
