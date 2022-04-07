import Grid from '@mui/material/Grid';
import React, { useEffect, useState } from 'react';
import { getAllUsers } from '../../services/PodcaseAPIService';
import { User } from '../../Types';

const [users, setUsers] = useState<User[]>([]);

useEffect(() => {
    getAllUsers(setUsers, (error: any) => {
        console.log(error);
    })
}, []);

const Users = () => {
    return (
        <Grid>

        </Grid>
    );
};

export default Users;
