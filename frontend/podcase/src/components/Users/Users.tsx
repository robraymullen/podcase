import Grid from '@mui/material/Grid';
import React, { useEffect, useState, useContext } from 'react';
import { getAllUsers } from '../../services/PodcaseAPIService';
import { User } from '../../Types';
import UserItem from './UserItem';
import { AppContext } from '../../context/context';
import { changeHeaderText } from '../../context/reducer';

const Users = () => {

    const [users, setUsers] = useState<User[]>([]);
    const { state, dispatch } = useContext(AppContext);

    useEffect(() => {
        getAllUsers(setUsers, (error: any) => {
            console.log(error);
        });
        dispatch(changeHeaderText(""));
    }, []);

    return (
        <Grid container spacing={2} key="userGrid">
            {
                users.map((user) => {
                    return <UserItem key={user.id} user={user}></UserItem>
                })
            }
        </Grid>
    );
};

export default Users;
