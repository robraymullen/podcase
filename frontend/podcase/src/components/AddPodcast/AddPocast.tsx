import Alert from "@mui/material/Alert";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Collapse from "@mui/material/Collapse";
import Grid from "@mui/material/Grid";
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import React, { useRef, useState, useContext, useEffect } from "react";
import AlertTitle from "@mui/material/AlertTitle";
import { AppContext } from "../../context/context";
import { changeHeaderText } from '../../context/reducer';
import { addUserSubscriptionFromRSS } from "../../services/PodcaseAPIService";
import { Message } from "../../Types";

const AddPodcast = () => {

    const rssLabel = "RSS Link";
    const [rssLink, setRSSLink] = useState<string>(rssLabel);

    const { state, dispatch } = useContext(AppContext);
    const [message, setMessage] = useState<Message>({
        title: "",
        severity: "info",
        text: "",
        visible: false,
    });

    const addSubscription = () => {
        if (state.currentUser) {
            addUserSubscriptionFromRSS(rssLink, "", state.currentUser.name, () => {
                setRSSLink("");
                setMessage({
                    severity: "info",
                    title: "Success",
                    visible: true,
                    text: "Podcast has been added to the archive"
                });
            }, () => {
                setMessage({
                    severity: "error",
                    title: "Error",
                    visible: true,
                    text: "There was a problem adding the podcast the the archive"
                });
            });
        } else {
            setMessage({
                severity: "error",
                text: "A user must be selected for subscribing to the podcast. ",
                visible: true,
                title: "No user logged in"
            });
        }
    };

    useEffect(() => {
        dispatch(changeHeaderText("Add Subscription"));
    }, []);

    return (
        <Box component="form"
            sx={{
                '& .MuiTextField-root': { m: 1, width: '25ch' },
            }}
            noValidate
            autoComplete="off">
            <Grid container spacing={2}>
                <TextField
                    fullWidth
                    required
                    id="outlined-required"
                    label={rssLabel}
                    defaultValue={rssLabel}
                    onChange={(event) => {setRSSLink(event.target.value); setMessage({...message, visible: false});}}
                />
                <Button variant="outlined" size="medium" onClick={addSubscription}>Add</Button>
            </Grid>
            <Box sx={{ width: '100%' }}>
                <Collapse in={message.visible}>
                    <Alert
                        severity={message.severity}
                        action={
                            <IconButton
                                aria-label="close"
                                color="inherit"
                                size="small"
                                onClick={() => {
                                    setMessage({...message, visible: false});
                                }}
                            >
                                <CloseIcon fontSize="inherit" />
                            </IconButton>
                        }
                        sx={{ mb: 2 }}
                    >
                        <AlertTitle>{message.title}</AlertTitle>
                        {message.text}
                </Alert>
                </Collapse>
            </Box>
        </Box>
    )
};

export default AddPodcast;