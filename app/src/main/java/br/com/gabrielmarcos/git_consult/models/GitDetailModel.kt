package br.com.gabrielmarcos.git_consult.models

/**
 * Created by Gabriel Marcos on 15/08/2018
 */
class GitDetailModel( val id: Int,
                      val name: String,
                      val owner: GitOwnerModel,
                      val description: String,
                      val forks_count: Int,
                      val stargazers_count: Int)
