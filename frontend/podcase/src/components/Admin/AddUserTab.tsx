import Alert from "@mui/material/Alert";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Collapse from "@mui/material/Collapse";
import Grid from "@mui/material/Grid";
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import React, { useRef, useState } from "react";
import AlertTitle from "@mui/material/AlertTitle";
import { addUser } from "../../services/PodcaseAPIService";

const AddUserTab = () => {

    const defaultName = "Name";
    const defaultPassword = "Password";

    const [showWarning, setShowWarning] = useState<boolean>(false);
    const [name, setName] = useState<string>(defaultName);
    const [password, setPassword] = useState<string>(defaultPassword);

    const postUser = (event: React.SyntheticEvent) => {
        if ((!name || ! password) || (name === "" || password === "")
            || (name === defaultName || password === defaultPassword)) { //terrible validation but it'll do for now
                setShowWarning(true);
        } else {
            addUser(name, password);
        }
    }

    return (
        <Box component="form"
            sx={{
                '& .MuiTextField-root': { m: 1, width: '25ch' },
            }}
            noValidate
            autoComplete="off">
            <Grid container spacing={2}>
                <TextField
                    required
                    id="outlined-required"
                    label="Username"
                    defaultValue={name}
                    onChange={(event) => {setName(event.target.value); setShowWarning(false);}}
                />
                <TextField
                    required
                    id="outlined-password-input"
                    label="Password"
                    type="password"
                    defaultValue=""
                    autoComplete="current-password"
                    onChange={(event) => {setPassword(event.target.value); setShowWarning(false);}}
                />
                <Button variant="outlined" size="medium" onClick={postUser}>Add</Button>
            </Grid>
            <Box sx={{ width: '100%' }}>
                <Collapse in={showWarning}>
                    <Alert
                        severity="error"
                        action={
                            <IconButton
                                aria-label="close"
                                color="inherit"
                                size="small"
                                onClick={() => {
                                    setShowWarning(false);
                                }}
                            >
                                <CloseIcon fontSize="inherit" />
                            </IconButton>
                        }
                        sx={{ mb: 2 }}
                    >
                        <AlertTitle>Username/Password combination not allowed</AlertTitle>
                        You must use a different username/password combination than the default setting.
                </Alert>
                </Collapse>
            </Box>
        </Box>
    )
};

export default AddUserTab;