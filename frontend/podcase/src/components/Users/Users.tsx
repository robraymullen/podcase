import Grid from '@mui/material/Grid';
import React, { useEffect, useState } from 'react';
import { getAllUsers } from '../../services/PodcaseAPIService';
import { User } from '../../Types';
import UserItem from './UserItem';

const Users = () => {

    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        getAllUsers(setUsers, (error: any) => {
            console.log(error);
        })
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
