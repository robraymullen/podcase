import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import Grid from "@mui/material/Grid";
import Box from '@mui/material/Box';
import { SubscribedEpisode, User } from "../../Types";
import { AppContext } from "../../context/context";
import { useContext } from 'react';
import { changeEpisode, changeUser, setAutoPlay } from '../../context/reducer';
import { getMostRecentPlayedEpisode } from "../../services/PodcaseAPIService";

interface UserItemProperties {
    user: User;
}

const UserItem = (userDetails: UserItemProperties) => {

    const { state, dispatch } = useContext(AppContext);
    const isSelectedUser = state.currentUser && userDetails.user.id === state.currentUser.id;//isSelectedUser ? "primary.main" : undefined
    return (
        <Grid item className="clickable">
            <Card className="clearClickable" sx={{ maxWidth: 250, maxHeight: 400, border: isSelectedUser ? 1 : 0, borderColor: isSelectedUser ? "primary.main" : undefined }} onClick={() => {
                dispatch(changeUser(userDetails.user));
                getMostRecentPlayedEpisode(userDetails.user.id, (episode: SubscribedEpisode) => {
                    dispatch(setAutoPlay(false));
                    dispatch(changeEpisode(episode));
                  }, () => { console.log("error getting most recently played for:"+userDetails.user.name) });
            }}>
                <CardContent>
                    <Typography variant="h5" component="div" align="center">
                        {userDetails.user.name}
                    </Typography>
                    <Box
                    display="flex"
                    alignItems="center"
                    justifyContent="center"
                    >
                        <img src={`${process.env.REACT_APP_PODCASE_IMAGE_URL+userDetails.user.imageUrl}`} width="75%" height="75%"/>
                    </Box>
                </CardContent>
            </Card>
        </Grid>
    );

};

export default UserItem;