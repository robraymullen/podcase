import { Box, CircularProgress } from '@mui/material';
import { useEffect, useState } from 'react';
import { DeadDownload } from '../../Types';
import { getDeadDownloads } from '../../services/PodcaseAPIService';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';


const DeadDownloadTab = () => {

    const [deadDownloads, setDeadDownloads] = useState<DeadDownload[]>();

    useEffect(() => {
        getDeadDownloads(setDeadDownloads, (exception: any) => {console.error(exception);})
    }, []);

    return (
        <Box>
            {
                deadDownloads && deadDownloads.length > 0 ?
                <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                  <TableHead>
                    <TableRow>
                      <TableCell>Episode Id</TableCell>
                      <TableCell align="right">Attempt Count</TableCell>
                      <TableCell align="right">Last Download attempt</TableCell>
                      <TableCell align="right">Title</TableCell>
                      <TableCell align="right">File URL</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {deadDownloads.map((download) => (
                      <TableRow
                        key={download.id+""}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                      >
                        <TableCell component="th" scope="row">
                          {download.episodeId}
                        </TableCell>
                        <TableCell align="right">{download.attemptCount}</TableCell>
                        <TableCell align="right">{download.lastDownloadAttempt}</TableCell>
                        <TableCell align="right">{download.title}</TableCell>
                        <TableCell align="right">{download.fileUrl}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>

                    : <CircularProgress />
            }
        </Box>
    )
};

export default DeadDownloadTab;