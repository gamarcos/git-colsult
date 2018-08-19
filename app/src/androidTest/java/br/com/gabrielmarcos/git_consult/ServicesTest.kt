package br.com.gabrielmarcos.git_consult

import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import br.com.gabrielmarcos.git_consult.services.GitPullService
import br.com.gabrielmarcos.git_consult.services.GitRepoService
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Gabriel Marcos on 19/08/2018
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class ServicesTest {
    companion object {
        const val TIME_OUT = 1000L
    }

    val PAGE = 1
    val USER = "ReactiveX"
    val PROJECT = "RxJava"

    var context = InstrumentationRegistry.getContext()
    var prService: GitPullService? = null
    var repoService: GitRepoService? = null

    /* Get Repositories */
    @Test
    fun testGetRepositories() {
        repoService?.subscrible(PAGE, {
            check(true)
        },{
            check(false, { "testRepositories: Failure" })
        })

        Thread.sleep(TIME_OUT)
    }

    /* Get Pull Request */
    @Test
    fun testGetPullRequest() {
        prService?.subscrible(USER, PROJECT,{
            check(true)
        },{
            check(false, { "testPullRequest: Failure" })
        })

        Thread.sleep(TIME_OUT)
    }
}